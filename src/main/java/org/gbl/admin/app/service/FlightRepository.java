package org.gbl.admin.app.service;

import org.gbl.admin.app.domain.Flight;
import org.gbl.kernel.domain.Identity;

import java.util.Optional;

public interface FlightRepository {
    void save(Flight flight);

    Optional<Flight> findById(Identity flightId);
}
