package org.gbl.booking.repository;

import org.gbl.booking.domain.Booking;
import org.gbl.booking.domain.BookingRepository;
import org.gbl.booking.domain.BookingStatus;
import org.gbl.booking.domain.SeatReservation;
import org.gbl.shared.domain.Identity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class BookingJPARepositoryImpl implements BookingRepository {

    private final BookingJPARepository jpaRepository;

    public BookingJPARepositoryImpl(BookingJPARepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Booking booking) {
        final var model = toModel(booking);
        jpaRepository.save(model);
    }

    private static BookingModel toModel(Booking booking) {
        var model = new BookingModel();
        model.id = booking.id().toString();
        model.flightId = booking.flightId().toString();
        model.email = booking.email();
        model.status = booking.status().toString();
        model.seats =
                booking.seatReservations().stream().map(seatReservation -> toModel(seatReservation, model)).toList();
        return model;
    }

    private static BookingSeatsModel toModel(SeatReservation seatReservation,
                                             BookingModel model) {
        var seatModel = new BookingSeatsModel();
        seatModel.seatId = seatReservation.seatId();
        seatModel.price = seatReservation.price();
        seatModel.bookingId = model.id;
        return seatModel;
    }

    @Override
    public Optional<Booking> findById(Identity bookingId) {
        return jpaRepository.findById(bookingId.uuid()).map(BookingJPARepositoryImpl::toDomain);
    }

    private static Booking toDomain(BookingModel model) {
        return new Booking(UUID.fromString(model.id), UUID.fromString(model.flightId),
                           model.email, BookingStatus.of(model.status),
                           model.seats.stream().map(BookingJPARepositoryImpl::todDomain).toList());
    }

    private static SeatReservation todDomain(BookingSeatsModel seatModel) {
        return new SeatReservation(seatModel.seatId, seatModel.price);
    }
}
