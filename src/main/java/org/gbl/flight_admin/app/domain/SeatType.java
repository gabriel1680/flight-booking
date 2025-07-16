package org.gbl.flight_admin.app.domain;

import org.gbl.shared.domain.DomainException;

public enum SeatType {
    ECONOMIC("economic"),
    EXECUTIVE("executive"),
    FIRST_CLASS("first class");

    private final String value;

    SeatType(String value) {
        this.value = value;
    }

    public static SeatType of(String aString) {
        for (var seatType : values()) {
            if (seatType.value.equals(aString)) {
                return seatType;
            }
        }
        throw new InvalidSeatTypeException();
    }

    public static class InvalidSeatTypeException extends DomainException {
        public InvalidSeatTypeException() {
            super("Invalid seat type");
        }
    }
}
