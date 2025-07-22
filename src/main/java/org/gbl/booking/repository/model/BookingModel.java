package org.gbl.booking.repository.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class BookingModel {
    @Id
    @Column(columnDefinition = "uuid")
    public UUID id;

    @Column(name = "flight_id", columnDefinition = "uuid")
    public UUID flightId;

    @Column(columnDefinition = "VARCHAR(25)")
    public String status;

    @Column(columnDefinition = "VARCHAR(255)")
    public String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    public List<BookingSeatsModel> seats;
}
