package org.gbl.flight_admin.app.domain;

import org.gbl.kernel.domain.DomainException;

public enum Availability {
    AVAILABLE,
    UNAVAILABLE;

    public static Availability of(int availability) {
        return switch (availability) {
            case 0 -> UNAVAILABLE;
            case 1 -> AVAILABLE;
            default -> throw new SeatAlreadyTakenException();
        };
    }

    public Availability take() {
        if (Availability.UNAVAILABLE == this) {
            throw new SeatAlreadyTakenException();
        }
        return Availability.UNAVAILABLE;
    }

    public static class SeatAlreadyTakenException extends DomainException {
        public SeatAlreadyTakenException() {
            super("The selected seat have already been taken");
        }
    }
}
