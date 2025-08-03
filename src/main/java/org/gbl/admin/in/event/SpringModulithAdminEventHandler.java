package org.gbl.admin.in.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gbl.admin.FlightAdminApi;
import org.gbl.admin.FlightAdminApi.BookSeatsRequest;
import org.gbl.booking.event.BookingCreated;
import org.springframework.context.event.EventListener;

public class SpringModulithAdminEventHandler {

    private static final Logger LOG = LogManager.getLogger(SpringModulithAdminEventHandler.class);

    private final FlightAdminApi flightAdminApi;

    public SpringModulithAdminEventHandler(FlightAdminApi flightAdminApi) {
        this.flightAdminApi = flightAdminApi;
    }

    @EventListener
    public void handle(BookingCreated event) {
        LOG.info("Creating booking [bookingId: {}] [flightId: {}]", event.bookingId(),
                 event.flightId());
        final var request = new BookSeatsRequest(event.bookingId(), event.flightId(),
                                                 event.seatIds());
        flightAdminApi.bookSeats(request);
    }
}
