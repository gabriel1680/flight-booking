package org.gbl.flight_admin.out.postgres.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @Column(columnDefinition = "smallint")
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    public List<PostgresSeatModel> seats;
}
