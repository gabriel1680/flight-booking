package org.gbl.booking.domain;

public enum BookingStatus {
    PENDING,
    SUCCESS,
    FAILED;

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
}
