package org.gbl.catalog.out.elasticsearch.document;

import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.elasticsearch.annotations.DateFormat;
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

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    public Instant boardingAt;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    public Instant landingAt;

    @CreatedDate
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    public Instant createdAt;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    public Instant updatedAt;

    @Field(type = FieldType.Nested, includeInParent = true)
    public Collection<SeatDocument> seats;
}
