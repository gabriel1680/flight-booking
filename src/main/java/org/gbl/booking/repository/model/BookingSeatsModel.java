package org.gbl.booking.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "booking_seats")
public class BookingSeatsModel {
    @Id
    @Column(name = "booking_id", columnDefinition = "uuid")
    public UUID bookingId;

    @Id
    @Column(name = "seat_id", columnDefinition = "uuid")
    public UUID seatId;

    @Column
    public double price;
}
