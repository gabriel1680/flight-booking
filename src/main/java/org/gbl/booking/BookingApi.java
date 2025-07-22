package org.gbl.booking;

import java.util.Collection;

public interface BookingApi {

    void book(BookRequest request);

    record BookRequest(
            String flightId,
            String email,
            Collection<SeatReservationRequest> seatReservations) {
    }

    record SeatReservationRequest(String seatId, double price) {
    }
}
