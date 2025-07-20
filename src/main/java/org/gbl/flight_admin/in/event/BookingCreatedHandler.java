package org.gbl.flight_admin.in.event;


import org.gbl.booking.event.BookingCreated;
import org.gbl.flight_admin.FlightAdminApi;
import org.gbl.flight_admin.FlightAdminApi.BookSeatsRequest;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class BookingCreatedHandler {

    private final FlightAdminApi flightAdminApi;

    public BookingCreatedHandler(FlightAdminApi flightAdminApi) {
        this.flightAdminApi = flightAdminApi;
    }

    @ApplicationModuleListener
    public void handle(BookingCreated event) {
        flightAdminApi.bookSeats(new BookSeatsRequest(event.flightId(), event.seatIds()));
    }
}
