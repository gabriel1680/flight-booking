package org.gbl.flight_admin.in.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gbl.booking.event.BookingCreated;
import org.gbl.flight_admin.FlightAdminApi;
import org.gbl.flight_admin.FlightAdminApi.BookSeatsRequest;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class BookingCreatedHandler {

    private static final Logger LOG = LogManager.getLogger(BookingCreatedHandler.class);

    private final FlightAdminApi flightAdminApi;

    public BookingCreatedHandler(FlightAdminApi flightAdminApi) {
        this.flightAdminApi = flightAdminApi;
    }

    @EventListener
    public void handle(BookingCreated event) {
        LOG.info("Processing BookingCreated event of bookingId: {}", event.bookingId());
        final var request = new BookSeatsRequest(event.bookingId(), event.flightId(),
                                                 event.seatIds());
        flightAdminApi.bookSeats(request);
    }
}
