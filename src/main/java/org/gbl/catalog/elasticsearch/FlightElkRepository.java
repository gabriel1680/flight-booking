package org.gbl.catalog.elasticsearch;

import org.gbl.catalog.elasticsearch.document.FlightDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FlightElkRepository extends ElasticsearchRepository<FlightDocument, String> {
}
