package org.gbl.catalog.application;

import org.gbl.catalog.infra.elasticsearch.document.FlightDocument;

import java.util.Optional;

public interface FlightDocumentRepository {
    void save(FlightDocument flightDocument);

    Optional<FlightDocument> getOf(String id);

    void delete(FlightDocument flightDocument);
}
