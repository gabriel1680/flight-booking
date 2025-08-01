package org.gbl.admin.app.domain;

import org.gbl.kernel.domain.DomainException;

public record Route(String origin, String destination) {
    public Route {
        if (origin == null || origin.isEmpty() || destination == null || destination.isEmpty()) {
            throw new InvalidRouteException();
        }
    }

    public static class InvalidRouteException extends DomainException {
        public InvalidRouteException() {
            super("Invalid Route: the fields cannot be empty");
        }
    }
}
