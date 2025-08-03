package org.gbl.booking.service;

import org.gbl.admin.FlightAdminApi;
import org.gbl.admin.FlightAdminApi.GetFlightRequest;
import org.gbl.booking.BookingApi;
import org.gbl.booking.domain.Booking;
import org.gbl.booking.domain.BookingRepository;
import org.gbl.booking.event.BookingCreated;
import org.gbl.kernel.application.ApplicationException;
import org.gbl.kernel.application.EventDispatcher;
import org.gbl.kernel.domain.Identity;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BookingApiImpl implements BookingApi {

    private final FlightAdminApi flightAdminApi;
    private final BookingRepository bookingRepository;
    private final EventDispatcher dispatcher;

    public BookingApiImpl(FlightAdminApi flightAdminApi, BookingRepository bookingRepository,
                          EventDispatcher dispatcher) {
        this.flightAdminApi = flightAdminApi;
        this.bookingRepository = bookingRepository;
        this.dispatcher = dispatcher;
    }

    @Override
    public void book(BookRequest request) {
        final var seatIds =
                request.seatReservations().stream().map(SeatReservationRequest::seatId).toList();
        if (bookingRepository.bookingExistsFor(request.flightId(), seatIds)) {
            throw new UnavailableSeatsForBooking(seatIds);
        }
        final var flight = flightAdminApi.getFlight(new GetFlightRequest(request.flightId()));
        final var unavailableSeats =
                seatIds.stream().filter(seatId -> !flight.seats().contains(seatId)).toList();
        if (!unavailableSeats.isEmpty()) {
            throw new UnavailableSeatsForBooking(unavailableSeats);
        }
        final var booking = Booking.create(request.flightId(), request.email());
        request.seatReservations().forEach(seatReservation -> booking.addSeatReservation(seatReservation.seatId(), seatReservation.price()));
        bookingRepository.save(booking);
        dispatcher.dispatch(new BookingCreated(booking));
    }

    public static class UnavailableSeatsForBooking extends ApplicationException {
        public UnavailableSeatsForBooking(Collection<String> unavailableSeats) {
            super("Unavailable seats selected: %s".formatted(unavailableSeats));
        }
    }

    public static class BookingNotFoundException extends ApplicationException {
        public BookingNotFoundException(String bookingId) {
            super("Booking with id '%s' not found".formatted(bookingId));
        }
    }


    @Override
    public void confirmBooking(String bookingId) {
        final var booking = getBookingFor(bookingId);
        booking.confirm();
        bookingRepository.save(booking);
    }

    @Override
    public void failBooking(String bookingId) {
        final var booking = getBookingFor(bookingId);
        booking.fail();
        bookingRepository.save(booking);
    }

    private Booking getBookingFor(String bookingId) {
        return bookingRepository.findById(Identity.of(bookingId))
                .orElseThrow(() -> new BookingNotFoundException(bookingId));
    }
}
