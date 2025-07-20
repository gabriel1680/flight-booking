package org.gbl.booking;

import java.util.List;

public interface BookingApi {

    void book(BookRequest request);

    record BookRequest(
            String flightId,
            String email,
            double price,
            List<SeatReservationRequest> seatReservations) {
    }

    record SeatReservationRequest(String seatId, double price) {
    }
}
