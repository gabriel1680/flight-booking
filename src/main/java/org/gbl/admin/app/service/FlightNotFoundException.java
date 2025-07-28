package org.gbl.admin.app.service;

import org.gbl.kernel.application.NotFoundException;

public class FlightNotFoundException extends NotFoundException {

    public FlightNotFoundException(String flightId) {
        super("Flight not found with id '%s'".formatted(flightId));
    }
}
