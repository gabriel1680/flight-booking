package org.gbl.booking;

import java.util.List;

public interface BookingApi {

    void book(BookRequest request);

    record BookRequest(String flightId, List<String> seatIds) {}
}
