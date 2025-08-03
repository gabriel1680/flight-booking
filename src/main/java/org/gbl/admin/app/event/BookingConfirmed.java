package org.gbl.admin.app.event;

public record BookingConfirmed(String bookingId) implements BookingProcessed {
    @Override
    public String name() {
        return "booking.confirmed";
    }
}
