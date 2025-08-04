package org.gbl.booking.event;

import org.gbl.booking.domain.Booking;
import org.gbl.booking.domain.SeatReservation;
import org.gbl.kernel.application.Event;

import java.util.Set;
import java.util.stream.Collectors;

public record BookingCreated(String bookingId, String flightId,
                             Set<String> seatIds) implements Event {

    public BookingCreated(Booking source) {
        this(source.id().value(), source.flightId().toString(),
             source.seatReservations().stream()
                     .map(SeatReservation::seatId)
                     .collect(Collectors.toSet()));
    }

    @Override
    public String name() {
        return "booking-created";
    }
}
