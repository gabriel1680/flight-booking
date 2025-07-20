package org.gbl.booking.service;

import org.gbl.booking.BookingApi;
import org.gbl.booking.domain.Booking;
import org.gbl.booking.BookingCreated;
import org.gbl.booking.repository.BookingRepository;
import org.gbl.flight_admin.FlightAdminApi;
import org.gbl.flight_admin.FlightAdminApi.GetFlightRequest;
import org.gbl.shared.application.ApplicationException;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collection;

public class BookingService implements BookingApi {

    private final FlightAdminApi flightAdminApi;
    private final BookingRepository bookingRepository;
    private final ApplicationEventPublisher eventPublisher;

    public BookingService(FlightAdminApi flightAdminApi, BookingRepository bookingRepository,
                          ApplicationEventPublisher eventPublisher) {
        this.flightAdminApi = flightAdminApi;
        this.bookingRepository = bookingRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void book(BookRequest request) {
        final var flight = flightAdminApi.getFlight(new GetFlightRequest(request.flightId()));
        final var seatIds =
                request.seatReservations().stream().map(SeatReservationRequest::seatId).toList();
        final var unavailableSeats =
                flight.seats().stream().filter(seatId -> !seatIds.contains(seatId)).toList();
        if (!unavailableSeats.isEmpty()) {
            throw new UnavailableSeatsForBooking(unavailableSeats);
        }
        final var booking = Booking.create(request.flightId(), request.email());
        request.seatReservations()
                .forEach(seatReservation ->
                                 booking.addSeatReservation(seatReservation.seatId(),
                                                            seatReservation.price()));
        bookingRepository.save(booking);
        final var bookingCreated = new BookingCreated(booking);
        eventPublisher.publishEvent(bookingCreated);
    }

    public static class UnavailableSeatsForBooking extends ApplicationException {
        public UnavailableSeatsForBooking(Collection<String> unavailableSeats) {
            super("Unavailable seats selected: %s".formatted(unavailableSeats));
        }
    }
}
