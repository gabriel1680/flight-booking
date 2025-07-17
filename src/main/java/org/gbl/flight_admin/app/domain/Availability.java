package org.gbl.flight_admin.app.domain;

import org.gbl.shared.domain.DomainException;

public enum Availability {
    AVAILABLE,
    UNAVAILABLE;

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
