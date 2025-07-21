package org.gbl.booking.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking_seats")
public class BookingSeatsModel {
    @Id
    @Column(name = "booking_id", columnDefinition = "uuid")
    public String bookingId;

    @Id
    @Column(name = "seat_id", columnDefinition = "uuid")
    public String seatId;

    @Column
    public double price;
}
