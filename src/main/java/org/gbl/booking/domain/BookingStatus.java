package org.gbl.booking.domain;

import org.gbl.shared.domain.DomainException;

public enum BookingStatus {
    PENDING,
    SUCCESS,
    FAILED;

    public static BookingStatus of(String status) {
        for (var value : values()) {
            if (value.name().toLowerCase().equals(status)) {
                return value;
            }
        }
        throw new InvalidBookingStatusException();
    }

    public BookingStatus confirm() {
        return switch (this) {
            case FAILED -> FAILED; // ignore already processed event
            case SUCCESS, PENDING -> SUCCESS;
        };
    }

    public BookingStatus fail() {
        return switch (this) {
            case SUCCESS -> SUCCESS; // ignore already processed event
            case FAILED, PENDING -> FAILED;
        };
    }

    public static class InvalidBookingStatusException extends DomainException {
        public InvalidBookingStatusException() {
            super("Invalid booking status");
        }
    }
}
