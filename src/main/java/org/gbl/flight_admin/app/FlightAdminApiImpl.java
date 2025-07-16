package org.gbl.flight_admin.app;

import org.gbl.flight_admin.FlightAdminApi;
import org.gbl.flight_admin.app.domain.Flight;
import org.gbl.flight_admin.app.domain.Seat;
import org.gbl.shared.domain.Identity;

import java.util.List;

public class FlightAdminApiImpl implements FlightAdminApi {

    private final FlightRepository flightRepository;
    private final FlightQueryService queryService;

    public FlightAdminApiImpl(FlightRepository flightRepository,
                              FlightQueryService queryService) {
        this.flightRepository = flightRepository;
        this.queryService = queryService;
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
        return queryService.findAll();
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
}
