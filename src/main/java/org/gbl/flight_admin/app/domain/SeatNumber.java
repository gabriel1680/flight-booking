package org.gbl.flight_admin.app.domain;

import org.gbl.shared.domain.DomainException;

import java.util.regex.Pattern;

public record SeatNumber(String value) {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]{6}$");

    public SeatNumber {
        if (value == null || value.isEmpty() || !NUMBER_PATTERN.matcher(value).matches()) {
            throw new InvalidSeatNumberException();
        }
    }

    public static class InvalidSeatNumberException extends DomainException {
        public InvalidSeatNumberException() {
            super("The seat number should have the pattern XXXXXX with all numbers");
        }
    }
}
