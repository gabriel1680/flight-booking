package org.gbl.catalog.out.elasticsearch;

import org.gbl.catalog.out.elasticsearch.document.FlightDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface FlightElkRepository extends ElasticsearchRepository<FlightDocument, String> {
    Optional<FlightDocument> findByIdAndIsAvailableTrue(String flightId);
}
