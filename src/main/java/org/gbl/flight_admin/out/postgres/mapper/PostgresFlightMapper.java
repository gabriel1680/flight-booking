package org.gbl.flight_admin.out.postgres.mapper;

import org.gbl.flight_admin.app.domain.Availability;
import org.gbl.flight_admin.app.domain.Capacity;
import org.gbl.flight_admin.app.domain.Flight;
import org.gbl.flight_admin.app.domain.Route;
import org.gbl.flight_admin.app.domain.Schedule;
import org.gbl.flight_admin.app.domain.Seat;
import org.gbl.flight_admin.app.domain.SeatNumber;
import org.gbl.flight_admin.app.domain.SeatType;
import org.gbl.flight_admin.out.postgres.model.PostgresFlightModel;
import org.gbl.flight_admin.out.postgres.model.PostgresSeatModel;
import org.gbl.shared.domain.Identity;

import java.util.Locale;

public class PostgresFlightMapper {

    public Flight toDomain(PostgresFlightModel model) {
        return Flight.hydrate(
                Identity.of(model.id),
                new Capacity(model.capacity),
                new Route(model.origin, model.destination),
                new Schedule(model.boardingAt, model.landingAt),
                model.seats.stream().map(this::toDomain).toList()
        );
    }

    public PostgresFlightModel toModel(Flight flight) {
        final var model = new PostgresFlightModel();
        model.id = flight.id().uuid();
        model.capacity = flight.capacity().value();
        model.origin = flight.route().origin();
        model.destination = flight.route().destination();
        model.boardingAt = flight.schedule().boardingAt();
        model.landingAt = flight.schedule().landingAt();
        model.seats = flight.seats().stream().map(this::toModel).toList();
        return model;
    }

    public Seat toDomain(PostgresSeatModel model) {
        return Seat.hydrate(
                Identity.of(model.id),
                new SeatNumber(model.number),
                SeatType.of(model.type),
                Availability.of(model.availability)
        );
    }

    public PostgresSeatModel toModel(Seat seat) {
        final var model = new PostgresSeatModel();
        model.id = seat.id().uuid();
        model.number = seat.number();
        model.type = seat.type().toLowerCase(Locale.ROOT);
        model.availability = seat.isAvailable() ? 1 : 0;
        return model;
    }
}
