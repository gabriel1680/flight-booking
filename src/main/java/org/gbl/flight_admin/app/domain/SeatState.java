package org.gbl.flight_admin.app.domain;

import org.gbl.shared.domain.DomainException;

public enum SeatState {
    AVAILABLE,
    UNAVAILABLE;

    public SeatState take() {
        if (SeatState.UNAVAILABLE == this) {
            throw new SeatAlreadyTakenException();
        }
        return SeatState.UNAVAILABLE;
    }

    public static class SeatAlreadyTakenException extends DomainException {
        public SeatAlreadyTakenException() {
            super("The selected seat have already been taken");
        }
    }
}
