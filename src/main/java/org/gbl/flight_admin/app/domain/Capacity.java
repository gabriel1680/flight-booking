package org.gbl.flight_admin.app.domain;

import org.gbl.shared.domain.DomainException;

public record Capacity(int value) {

    public Capacity {
        if (value < 0) {
            throw new InvalidCapacityException();
        }
    }

    public static class InvalidCapacityException extends DomainException {
        public InvalidCapacityException() {
            super("The capacity should be greater than 0");
        }
    }
}
