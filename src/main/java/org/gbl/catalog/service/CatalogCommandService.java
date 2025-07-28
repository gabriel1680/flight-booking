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

    public void handle(CreateFlightCommand dto) {
        repository.save(toDocument(dto));
    }

    private static FlightDocument toDocument(CreateFlightCommand command) {
        final var document = new FlightDocument();
        document.id = command.id();
        document.origin = command.origin();
        document.destination = command.destination();
        document.boardingAt = command.boardingAt();
        document.landingAt = command.landingAt();
        document.available = command.seats.stream().anyMatch(seat -> seat.availability == 1);
        document.seats = command.seats.stream().map(CatalogCommandService::toDocument).toList();
        document.createdAt = command.createdAt();
        document.updatedAt = command.updatedAt();
        return document;
    }

    private static SeatDocument toDocument(SeatCreatedDto seat) {
        final var seatDocument = new SeatDocument();
        seatDocument.id = seat.id();
        seatDocument.number = seat.number();
        seatDocument.availability = seat.availability();
        return seatDocument;
    }

    public void handle(DeleteFlightCommand command) {
        repository.findById(command.flightId()).ifPresentOrElse(
                repository::delete,
                () -> LOG.error("Flight document not found for delete with flightId '{}'", command.flightId()));
    }

    public record DeleteFlightCommand(String flightId) {}

    public record CreateFlightCommand(
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
