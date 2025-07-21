package org.gbl.flight_admin.app.domain;

import org.gbl.kernel.domain.Identity;

import java.util.Objects;

public class Seat {

    private final Identity id;
    private final SeatNumber number;
    private final SeatType type;
    private final Availability availability;

    private Seat(Identity id, SeatNumber number, SeatType type, Availability availability) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.availability = availability;
    }

    public static Seat create(String number, String type) {
        return new Seat(Identity.nextId(), new SeatNumber(number), SeatType.of(type),
                        Availability.AVAILABLE);
    }

    public static Seat hydrate(Identity id, SeatNumber number, SeatType type,
                               Availability availability) {
        return new Seat(id, number, type, availability);
    }

    public Identity id() {
        return id;
    }

    public String number() {
        return number.value();
    }

    public String type() {
        return type.value();
    }

    public boolean isAvailable() {
        return availability.equals(Availability.AVAILABLE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(id, seat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void book() {
        availability.take();
    }
}
