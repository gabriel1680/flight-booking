package org.gbl.catalog.infra.elasticsearch;

import org.gbl.catalog.infra.elasticsearch.document.FlightDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FlightElkRepository extends ElasticsearchRepository<FlightDocument, String> {
}
