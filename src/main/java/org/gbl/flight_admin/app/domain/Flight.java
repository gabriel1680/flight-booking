package org.gbl.flight_admin.app.domain;

import org.gbl.shared.domain.DomainException;
import org.gbl.shared.domain.Identity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Flight {
    private final Identity id;
    private Capacity capacity;
    private Route route;
    private Schedule schedule;
    private final List<Seat> seats;

    private Flight(Identity id, Capacity capacity, Route route, Schedule schedule,
                   List<Seat> seats) {
        this.id = id;
        this.capacity = capacity;
        this.route = route;
        this.schedule = schedule;
        this.seats = seats;
    }

    public static Flight create(int capacity, String origin, String destination,
                                Instant boardingAt, Instant landingAt) {
        return new Flight(
                Identity.nextId(),
                new Capacity(capacity),
                new Route(origin, destination),
                new Schedule(boardingAt, landingAt),
                new ArrayList<>());
    }

    public static Flight hydrate(Identity id, Capacity capacity, Route route, Schedule schedule,
                                 List<Seat> seats) {
        return new Flight(id, capacity, route, schedule, seats);
    }

    public Identity id() {
        return id;
    }

    public Capacity capacity() {
        return capacity;
    }

    public Route route() {
        return route;
    }

    public Schedule schedule() {
        return schedule;
    }

    public List<Seat> seats() {
        return Collections.unmodifiableList(seats);
    }

    public void addSeat(String number, String type) {
        if (seats.size() == capacity.value()) {
            throw new FlightOutOfCapacityException();
        }
        seats.add(Seat.create(number, type));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(id, flight.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static class FlightOutOfCapacityException extends DomainException {
        public FlightOutOfCapacityException() {
            super("Flight exceeded the seats capacity");
        }
    }
}
