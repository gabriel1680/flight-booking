package org.gbl.booking;

import org.gbl.booking.domain.Booking;
import org.gbl.booking.domain.SeatReservation;

import java.util.List;

public record BookingCreated(String bookingId, String flightId, List<String> seatIds) {

    public BookingCreated(Booking source) {
        this(source.id().toString(), source.flightId().toString(),
             source.seatReservations().stream()
                     .map(SeatReservation::seatId)
                     .toList());
    }
}
