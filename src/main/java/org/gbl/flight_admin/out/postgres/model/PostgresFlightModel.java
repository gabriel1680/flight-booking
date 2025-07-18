package org.gbl.flight_admin.out.postgres.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "flights")
public class PostgresFlightModel {
    @Id
    @Column(columnDefinition = "uuid")
    public UUID id;

    @Column(columnDefinition = "uint16")
    public int capacity;

    @Column(columnDefinition = "VARCHAR(255)")
    public String origin;

    @Column(columnDefinition = "VARCHAR(255)")
    public String destination;

    @Column
    public Instant boardingAt;

    @Column
    public Instant landingAt;

    @CreationTimestamp
    public Instant createdAt;

    @UpdateTimestamp
    public Instant updatedAt;

    @OneToMany
    public List<PostgresSeatModel> seats;
}
