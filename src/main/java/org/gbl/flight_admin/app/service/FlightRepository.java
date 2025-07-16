package org.gbl.flight_admin.app.service;

import org.gbl.flight_admin.app.domain.Flight;
import org.gbl.shared.domain.Identity;

import java.util.Optional;

public interface FlightRepository {
    void save(Flight flight);

    Optional<Flight> findById(Identity flightId);
}
