package org.gbl.admin.app.event;

public record BookingFailed(String bookingId) implements BookingProcessed {
    @Override
    public String name() {
        return "booking-failed";
    }
}
