package org.gbl.catalog.in.kafka.model;

// Event notification - decouples the table structure with the flight DTO
public record FlightCreatedPayload(String id) {
}
