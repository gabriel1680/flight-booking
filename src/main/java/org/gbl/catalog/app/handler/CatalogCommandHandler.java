package org.gbl.catalog.app.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gbl.catalog.out.elasticsearch.FlightElkRepository;
import org.gbl.catalog.out.elasticsearch.document.FlightDocument;
import org.gbl.catalog.out.elasticsearch.document.SeatDocument;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;

@Service
public class CatalogCommandHandler {
    private static final Logger LOG = LogManager.getLogger(CatalogCommandHandler.class);

    private final FlightElkRepository repository;

    public CatalogCommandHandler(FlightElkRepository repository) {
        this.repository = repository;
    }

    public void handle(SaveFlightCommand dto) {
        repository.save(toDocument(dto));
    }

    private static FlightDocument toDocument(SaveFlightCommand command) {
        final var document = new FlightDocument();
        document.id = command.id();
        document.origin = command.origin();
        document.destination = command.destination();
        document.boardingAt = command.boardingAt();
        document.landingAt = command.landingAt();
        document.isAvailable = command.seats().stream().anyMatch(CreateSeatDto::isAvailable);
        document.seats = command.seats().stream().map(CatalogCommandHandler::toDocument).toList();
        return document;
    }

    private static SeatDocument toDocument(CreateSeatDto seat) {
        final var seatDocument = new SeatDocument();
        seatDocument.id = seat.id();
        seatDocument.number = seat.number();
        seatDocument.isAvailable = seat.isAvailable();
        return seatDocument;
    }

    public void handle(DeleteFlightCommand command) {
        repository.findById(command.flightId()).ifPresentOrElse(
                repository::delete,
                () -> LOG.error("Flight document not found for delete with flightId '{}'", command.flightId()));
    }

    public record DeleteFlightCommand(String flightId) {}

    public record SaveFlightCommand(
            String id,
            String origin,
            String destination,
            Instant boardingAt,
            Instant landingAt,
            Collection<CreateSeatDto> seats) {}

    public record CreateSeatDto(String id, String type, String number, boolean isAvailable) {}
}
