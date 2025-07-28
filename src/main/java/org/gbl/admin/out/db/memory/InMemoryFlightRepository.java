package org.gbl.admin.out.db.memory;

import org.gbl.admin.FlightAdminApi.FlightQueryResponse;
import org.gbl.admin.app.domain.Flight;
import org.gbl.admin.app.domain.Seat;
import org.gbl.admin.app.service.FlightQueryService;
import org.gbl.admin.app.service.FlightRepository;
import org.gbl.kernel.domain.Identity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InMemoryFlightRepository implements FlightRepository, FlightQueryService {

    private final Map<Identity, Flight> flights;

    public InMemoryFlightRepository(List<Flight> flights) {
        this.flights = flights.stream().collect(Collectors.toMap(
                Flight::id,
                Function.identity(),
                (existing, replacement) -> replacement
        ));
    }

    @Override
    public void save(Flight flight) {
        flights.put(flight.id(), flight);
    }

    @Override
    public Optional<Flight> findById(Identity flightId) {
        return Optional.ofNullable(flights.get(flightId));
    }

    @Override
    public List<FlightQueryResponse> searchFlights() {
        return flights.values().stream().map(flight -> {
            final var capacity = flight.capacity().value();
            final var seats = flight.seats();
            final var availableSeats =
                    (int) seats.stream().filter(Seat::isAvailable).count();
            return new FlightQueryResponse(
                    flight.id().value(),
                    capacity,
                    seats.size(),
                    availableSeats);
        }).toList();
    }
}
