package org.gbl.admin.app.domain;

import org.gbl.kernel.domain.DomainException;

import java.util.Locale;

public enum SeatType {
    ECONOMIC("economic"),
    EXECUTIVE("executive"),
    FIRST_CLASS("first class");

    private final String value;

    SeatType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
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

    @Override
    public String toString() {
        return value.toLowerCase(Locale.ROOT);
    }
}
