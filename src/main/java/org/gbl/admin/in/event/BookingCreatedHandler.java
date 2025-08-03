package org.gbl.admin.in.event;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gbl.admin.FlightAdminApi;
import org.gbl.admin.FlightAdminApi.BookSeatsRequest;
import org.gbl.booking.event.BookingCreated;
import org.gbl.kernel.application.EventHandler;
import org.springframework.stereotype.Service;

@Service
public class BookingCreatedHandler implements EventHandler<BookingCreated> {

    private static final Logger LOG = LogManager.getLogger(BookingCreatedHandler.class);

    private final FlightAdminApi flightAdminApi;

    public BookingCreatedHandler(FlightAdminApi flightAdminApi) {
        this.flightAdminApi = flightAdminApi;
    }

    public void handle(BookingCreated event) {
        LOG.info("Processing BookingCreated event of bookingId: {}", event.bookingId());
        final var request = new BookSeatsRequest(event.bookingId(), event.flightId(),
                                                 event.seatIds());
        flightAdminApi.bookSeats(request);
    }
}
