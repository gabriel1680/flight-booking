package org.gbl.booking.domain;

import org.gbl.kernel.domain.Identity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class Booking {

    private final Identity id;
    private final UUID flightId;
    private final String email;
    private BookingStatus status;
    private final Collection<SeatReservation> seatReservations;

    public Booking(Identity id, UUID flightId, String email, BookingStatus status,
                   Collection<SeatReservation> seatReservations) {
        this.id = id;
        this.flightId = flightId;
        this.email = email;
        this.status = status;
        this.seatReservations = new ArrayList<>(seatReservations);
    }

    public static Booking create(String flightId, String email) {
        return new Booking(Identity.nextId(), UUID.fromString(flightId), email,
                           BookingStatus.PENDING, new ArrayList<>());
    }

    public void addSeatReservation(String seatId, double price) {
        seatReservations.add(new SeatReservation(seatId, price));
    }

    public void confirm() {
        status = status.confirm();
    }

    public void fail() {
        status = status.fail();
    }

    public Identity id() {
        return id;
    }

    public UUID flightId() {
        return flightId;
    }

    public String email() {
        return email;
    }

    public BookingStatus status() {
        return status;
    }

    public Collection<SeatReservation> seatReservations() {
        return Collections.unmodifiableCollection(seatReservations);
    }
}
