package org.gbl.booking.domain;

import org.gbl.kernel.domain.DomainException;

public enum BookingStatus {
    PENDING,
    CONFIRMED,
    FAILED;

    public static BookingStatus of(String status) {
        for (var value : values()) {
            if (value.name().equals(status)) {
                return value;
            }
        }
        throw new InvalidBookingStatusException();
    }

    public BookingStatus confirm() {
        return switch (this) {
            case FAILED -> FAILED; // ignore already processed event
            case CONFIRMED, PENDING -> CONFIRMED;
        };
    }

    public BookingStatus fail() {
        return switch (this) {
            case CONFIRMED -> CONFIRMED; // ignore already processed event
            case FAILED, PENDING -> FAILED;
        };
    }

    public static class InvalidBookingStatusException extends DomainException {
        public InvalidBookingStatusException() {
            super("Invalid booking status");
        }
    }
}
