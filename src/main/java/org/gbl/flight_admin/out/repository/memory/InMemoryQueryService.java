package org.gbl.flight_admin.out.repository.memory;

import org.gbl.flight_admin.FlightAdminApi.FlightQueryResponse;
import org.gbl.flight_admin.app.FlightQueryService;
import org.gbl.flight_admin.app.domain.Flight;

import java.util.List;

public class InMemoryQueryService implements FlightQueryService {

    private final List<Flight> flights;

    public InMemoryQueryService(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public List<FlightQueryResponse> findAll() {
        return flights.stream().map(flight -> {
            final var capacity = flight.capacity().value();
            final var seatsCount = flight.seats().size();
            return new FlightQueryResponse(flight.id().value(), capacity, capacity - seatsCount);
        }).toList();
    }
}
