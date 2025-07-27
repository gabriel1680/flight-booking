package org.gbl.catalog.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gbl.catalog.elasticsearch.FlightElkRepository;
import org.gbl.catalog.elasticsearch.document.FlightDocument;
import org.gbl.catalog.elasticsearch.document.SeatDocument;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;

@Service
public class CatalogCommandService {
    private static final Logger LOG = LogManager.getLogger(CatalogCommandService.class);

    private final FlightElkRepository repository;

    public CatalogCommandService(FlightElkRepository repository) {
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
        document.seats = dto.seats.stream().map(CatalogCommandService::toDocument).toList();
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
        repository.findById(id).ifPresentOrElse(
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
