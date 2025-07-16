package org.gbl.flight_admin.app.domain;

import org.gbl.shared.domain.Identity;

import java.util.Objects;

public class Seat {

    private final Identity id;
    private final SeatNumber number;
    private final SeatType type;

    private Seat(Identity id, SeatNumber number, SeatType type) {
        this.id = id;
        this.number = number;
        this.type = type;
    }

    public static Seat create(String number, String type) {
        return new Seat(Identity.nextId(), new SeatNumber(number), SeatType.of(type));
    }

    public Identity id() {
        return id;
    }

    public SeatNumber number() {
        return number;
    }

    public SeatType type() {
        return type;
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
}
