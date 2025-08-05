package org.gbl.admin.out.db.postgres.mapper;

import org.gbl.admin.app.domain.Availability;
import org.gbl.admin.app.domain.Capacity;
import org.gbl.admin.app.domain.Flight;
import org.gbl.admin.app.domain.Route;
import org.gbl.admin.app.domain.Schedule;
import org.gbl.admin.app.domain.Seat;
import org.gbl.admin.app.domain.SeatNumber;
import org.gbl.admin.app.domain.SeatType;
import org.gbl.admin.out.db.postgres.model.PostgresSeatModel;
import org.gbl.admin.out.db.postgres.model.PostgresFlightModel;
import org.gbl.kernel.domain.Identity;

import java.util.UUID;

public class PostgresFlightMapper {

    public Flight toDomain(PostgresFlightModel model) {
        return Flight.hydrate(
                new Identity(model.id),
                new Capacity(model.capacity),
                new Route(model.origin, model.destination),
                new Schedule(model.boardingAt, model.landingAt),
                model.seats.stream().map(this::toDomain).toList()
        );
    }

    public PostgresFlightModel toModel(Flight flight) {
        final var model = new PostgresFlightModel();
        final var flightId = flight.id().uuid();
        model.id = flightId;
        model.capacity = flight.capacity().value();
        model.origin = flight.route().origin();
        model.destination = flight.route().destination();
        model.boardingAt = flight.schedule().boardingAt();
        model.landingAt = flight.schedule().landingAt();
        model.seats = flight.seats().stream().map(seat -> toModel(flightId, seat)).toList();
        return model;
    }

    public Seat toDomain(PostgresSeatModel model) {
        return Seat.hydrate(
                new Identity(model.id),
                new SeatNumber(model.number),
                SeatType.of(model.type),
                Availability.of(model.availability)
        );
    }

    public PostgresSeatModel toModel(UUID flightId, Seat seat) {
        final var model = new PostgresSeatModel();
        model.id = seat.id().uuid();
        model.flightId = flightId;
        model.number = seat.number().value();
        model.type = seat.type().value();
        model.availability = seat.isAvailable() ? 1 : 0;
        return model;
    }
}
