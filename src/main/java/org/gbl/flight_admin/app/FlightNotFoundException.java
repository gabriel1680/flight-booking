package org.gbl.flight_admin.app;

import org.gbl.shared.application.NotFoundException;

public class FlightNotFoundException extends NotFoundException {

    public FlightNotFoundException(String flightId) {
        super("Flight not found with id '%s'".formatted(flightId));
    }
}
