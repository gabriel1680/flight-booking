package org.gbl.flight_admin.out.db.postgres.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "flight_seats")
public class PostgresSeatModel {
    @Id
    @Column(columnDefinition = "uuid")
    public UUID id;

    @Column(name = "flight_id", columnDefinition = "uuid")
    public UUID flightId;

    @Column(columnDefinition = "VARCHAR(6)")
    public String number;

    @Column(columnDefinition = "VARCHAR(20)")
    public String type;

    @Column(columnDefinition = "smallint")
    public int availability;

    @CreationTimestamp
    public Instant createdAt;

    @UpdateTimestamp
    public Instant updatedAt;
}
