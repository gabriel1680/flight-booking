package org.gbl.flight_admin.app.domain;

import org.gbl.shared.domain.DomainException;

import java.time.Instant;

public record Schedule(Instant boardingAt, Instant landingAt) {
    public Schedule {
        if (boardingAt == null || landingAt == null) {
            throw new InvalidScheduleException("The boarding and landing time cannot be empty");
        } else if (boardingAt.isAfter(landingAt)) {
            throw new InvalidScheduleException("The boarding time should be before the landing " +
                                                       "time");
        }
    }

    public static class InvalidScheduleException extends DomainException {
        public InvalidScheduleException(String message) {
            super(message);
        }
    }
}
