package org.gbl.booking.event;

import org.gbl.admin.app.event.BookingConfirmed;
import org.gbl.admin.app.event.BookingFailed;
import org.gbl.booking.BookingApi;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class SpringModulithBookingEventHandler {

    private final BookingApi bookingApi;

    public SpringModulithBookingEventHandler(BookingApi bookingApi) {
        this.bookingApi = bookingApi;
    }

    @EventListener
    public void handle(BookingConfirmed event) {
        bookingApi.confirmBooking(event.bookingId());
    }

    @EventListener
    public void handle(BookingFailed event) {
        bookingApi.confirmBooking(event.bookingId());
    }
}
