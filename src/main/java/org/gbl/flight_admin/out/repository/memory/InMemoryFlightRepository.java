package org.gbl.flight_admin.out.repository.memory;

import org.gbl.flight_admin.app.FlightRepository;
import org.gbl.flight_admin.app.domain.Flight;
import org.gbl.shared.domain.Identity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InMemoryFlightRepository implements FlightRepository {

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
}
