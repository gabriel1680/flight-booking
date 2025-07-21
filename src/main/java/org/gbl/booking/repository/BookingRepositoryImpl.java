package org.gbl.booking.repository;

import org.gbl.booking.domain.Booking;
import org.gbl.booking.domain.BookingRepository;
import org.gbl.booking.domain.BookingStatus;
import org.gbl.booking.domain.SeatReservation;
import org.gbl.kernel.domain.Identity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class BookingRepositoryImpl implements BookingRepository {

    private final BookingJPARepository jpaRepository;

    public BookingRepositoryImpl(BookingJPARepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Booking booking) {
        final var model = toModel(booking);
        jpaRepository.save(model);
    }

    private static BookingModel toModel(Booking booking) {
        var model = new BookingModel();
        model.id = booking.id().uuid();
        model.flightId = booking.flightId();
        model.email = booking.email();
        model.status = booking.status().toString();
        model.seats =
                booking.seatReservations().stream().map(seatReservation -> toModel(seatReservation, model)).toList();
        return model;
    }

    private static BookingSeatsModel toModel(SeatReservation seatReservation,
                                             BookingModel model) {
        var seatModel = new BookingSeatsModel();
        seatModel.seatId = UUID.fromString(seatReservation.seatId());
        seatModel.price = seatReservation.price();
        seatModel.bookingId = model.id;
        return seatModel;
    }

    @Override
    public Optional<Booking> findById(Identity bookingId) {
        return jpaRepository.findById(bookingId.uuid()).map(BookingRepositoryImpl::toDomain);
    }

    private static Booking toDomain(BookingModel model) {
        return new Booking(Identity.of(model.id), model.flightId,
                           model.email, BookingStatus.of(model.status),
                           model.seats.stream().map(BookingRepositoryImpl::todDomain).toList());
    }

    private static SeatReservation todDomain(BookingSeatsModel seatModel) {
        return new SeatReservation(seatModel.seatId.toString(), seatModel.price);
    }
}
