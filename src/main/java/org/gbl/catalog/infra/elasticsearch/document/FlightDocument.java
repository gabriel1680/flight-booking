package org.gbl.catalog.infra.elasticsearch.document;

import jakarta.persistence.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.util.Collection;

@Document(indexName = "flight")
public class FlightDocument {
    @Id
    public String id;

    public String origin;

    public String destination;

    public boolean available;

    public Instant boardingAt;

    public Instant landingAt;

    public Instant createdAt;

    public Instant updatedAt;

    @Field(type = FieldType.Nested, includeInParent = true)
    public Collection<SeatDocument> seats;
}
