package org.gbl.admin;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;

public interface FlightAdminApi {
    void createFlight(CreateFlightRequest request);

    record CreateFlightRequest(int capacity, String origin, String destination, Instant boardingAt,
                               Instant landingAt) {}

    void addSeats(AddFlightSeatsRequest request);

    record AddFlightSeatsRequest(String flightId, Collection<AddFlightSeatRequest> seats) {}

    record AddFlightSeatRequest(String number, String type) {}

    Collection<FlightQueryResponse> getFlights();

    record FlightQueryResponse(String id, int capacity, int totalSeats, int availableSeats) {}

    GetFlightResponse getFlight(GetFlightRequest request);

    record GetFlightRequest(String id) {}

    record GetFlightResponse(String id, int capacity, String origin, String destination,
                             Instant boardingAt, Instant landingAt, Set<String> seats) {}

    void bookSeats(BookSeatsRequest request);

    record BookSeatsRequest(String bookingId, String flightId, Set<String> seatIds) {}
}
