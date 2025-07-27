package org.gbl.catalog.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gbl.catalog.infra.elasticsearch.document.FlightDocument;
import org.gbl.catalog.infra.elasticsearch.document.SeatDocument;

import java.time.Instant;
import java.util.Collection;

public class CatalogService {
    private static final Logger LOG = LogManager.getLogger(CatalogService.class);

    private final FlightDocumentRepository repository;

    public CatalogService(FlightDocumentRepository repository) {
        this.repository = repository;
    }

    public void create(FlightCreatedDto dto) {
        repository.save(toDocument(dto));
    }

    private static FlightDocument toDocument(FlightCreatedDto dto) {
        final var document = new FlightDocument();
        document.id = dto.id();
        document.origin = dto.origin();
        document.destination = dto.destination();
        document.boardingAt = dto.boardingAt();
        document.landingAt = dto.landingAt();
        document.available = dto.seats.stream().anyMatch(seat -> seat.availability == 1);
        document.seats = dto.seats.stream().map(CatalogService::toDocument).toList();
        document.createdAt = dto.createdAt();
        document.updatedAt = dto.updatedAt();
        return document;
    }

    private static SeatDocument toDocument(SeatCreatedDto seat) {
        final var seatDocument = new SeatDocument();
        seatDocument.id = seat.id();
        seatDocument.number = seat.number();
        seatDocument.availability = seat.availability();
        return seatDocument;
    }

    public void delete(String id) {
        repository.getOf(id).ifPresentOrElse(
                repository::delete,
                () -> LOG.error("Flight document not found for delete with id '{}'", id));
    }

    public record FlightCreatedDto(
            String id,
            String origin,
            String destination,
            Instant boardingAt,
            Instant landingAt,
            Collection<SeatCreatedDto> seats,
            Instant createdAt,
            Instant updatedAt) {}

    public record SeatCreatedDto(String id, String number, int availability) {}
}
