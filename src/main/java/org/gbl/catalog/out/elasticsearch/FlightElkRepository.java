package org.gbl.catalog.out.elasticsearch;

import org.gbl.catalog.out.elasticsearch.document.FlightDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FlightElkRepository extends ElasticsearchRepository<FlightDocument, String> {
}
