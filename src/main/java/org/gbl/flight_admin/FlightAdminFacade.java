package org.gbl.flight_admin;

import org.gbl.flight_admin.app.event.BookingConfirmed;
import org.gbl.flight_admin.app.event.BookingFailed;
import org.gbl.flight_admin.app.domain.Flight;
import org.gbl.flight_admin.app.domain.Seat;
import org.gbl.flight_admin.app.service.EventDispatcher;
import org.gbl.flight_admin.app.service.FlightNotFoundException;
import org.gbl.flight_admin.app.service.FlightQueryService;
import org.gbl.flight_admin.app.service.FlightRepository;
import org.gbl.kernel.domain.Identity;

import java.util.List;

public class FlightAdminFacade implements FlightAdminApi {

    private final FlightRepository flightRepository;
    private final FlightQueryService queryService;
    private final EventDispatcher eventDispatcher;

    public FlightAdminFacade(FlightRepository flightRepository,
                             FlightQueryService queryService, EventDispatcher eventDispatcher) {
        this.flightRepository = flightRepository;
        this.queryService = queryService;
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public void createFlight(CreateFlightRequest request) {
        flightRepository.save(toFlight(request));
    }

    private Flight toFlight(CreateFlightRequest request) {
        return Flight.create(
                request.capacity(),
                request.origin(), request.destination(),
                request.boardingAt(), request.landingAt());
    }

    @Override
    public void addSeats(AddFlightSeatsRequest request) {
        final var flight = getFlightFor(request.flightId());
        request.seats().forEach(seatRequest -> {
            flight.addSeat(seatRequest.number(), seatRequest.type());
        });
        flightRepository.save(flight);
    }

    @Override
    public List<FlightQueryResponse> getFlights() {
        return queryService.searchFlights();
    }

    @Override
    public GetFlightResponse getFlight(GetFlightRequest request) {
        return toOutput(getFlightFor(request.id()));
    }

    private Flight getFlightFor(String id) {
        final var flightId = Identity.of(id);
        return flightRepository.findById(flightId)
                .orElseThrow(() -> new FlightNotFoundException(flightId.value()));
    }

    private GetFlightResponse toOutput(Flight flight) {
        return new GetFlightResponse(
                flight.id().value(),
                flight.capacity().value(),
                flight.route().origin(),
                flight.route().destination(),
                flight.schedule().boardingAt(),
                flight.schedule().landingAt(),
                flight.seats().stream().map(Seat::id).map(Identity::value).toList());
    }

    @Override
    public void bookSeats(BookSeatsRequest request) {
        try {
            final var flight = getFlightFor(request.flightId());
            flight.bookSeats(request.seatIds());
            flightRepository.save(flight);
            eventDispatcher.dispatch(new BookingConfirmed(request.bookingId()));
        } catch (RuntimeException e) {
            eventDispatcher.dispatch(new BookingFailed(request.bookingId()));
        }
    }
}
