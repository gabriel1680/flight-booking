package org.gbl.flight_admin;

import java.time.Instant;
import java.util.List;

public interface FlightAdminApi {
    void createFlight(CreateFlightRequest request);

    record CreateFlightRequest(int capacity, String origin, String destination, Instant boardingAt,
                               Instant landingAt) {}

    void addSeats(AddFlightSeatsRequest request);

    record AddFlightSeatsRequest(String flightId, List<AddFlightSeatRequest> seats) {}

    record AddFlightSeatRequest(String number, String type) {}

    List<FlightQueryResponse> getFlights();

    record FlightQueryResponse(String id, int capacity, int seatsRemaining) {}

    GetFlightResponse getFlight(GetFlightRequest request);

    record GetFlightRequest(String id) {}

    record GetFlightResponse(String id, int capacity, String origin, String destination,
                             Instant boardingAt, Instant landingAt, List<String> seats) {}
}
