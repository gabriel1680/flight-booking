package org.gbl.catalog.infra.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.gbl.catalog.CatalogApi;
import org.gbl.catalog.dto.CatalogDto.GetFlightCatalogDto;
import org.gbl.catalog.dto.CatalogDto.ItineraryDto;
import org.gbl.catalog.dto.CatalogDto.Pagination;
import org.gbl.catalog.dto.CatalogDto.ScheduleDto;
import org.gbl.catalog.dto.CatalogDto.SearchBookingCatalogQuery;
import org.gbl.catalog.dto.CatalogDto.SearchFlightsCatalogDto;
import org.gbl.catalog.infra.elasticsearch.document.FlightDocument;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchOperations;

import static java.util.Arrays.asList;
import static org.gbl.catalog.infra.elasticsearch.FlightElkQueryBuilder.availableOnly;
import static org.gbl.catalog.infra.elasticsearch.FlightElkQueryBuilder.destinationOn;
import static org.gbl.catalog.infra.elasticsearch.FlightElkQueryBuilder.originatedOn;

public class ElasticSearchCatalogApi implements CatalogApi {

    private final SearchOperations searchOperations;

    public ElasticSearchCatalogApi(SearchOperations searchOperations) {
        this.searchOperations = searchOperations;
    }

    @Override
    public Pagination searchFlights(SearchBookingCatalogQuery query) {
        final var searchQuery = NativeQuery.builder()
                .withQuery(elkQueryOf(query))
                .withPageable(PageRequest.of(query.page(), query.size()))
                .build();
        final var result = searchOperations.search(searchQuery, FlightDocument.class);
        final var documents = result.getSearchHits().stream()
                .map(SearchHit::getContent)
                .map(ElasticSearchCatalogApi::toDto)
                .toList();
        final var total = (int) result.getTotalHits();
        return new Pagination(query.page(), total, query.size(), documents);
    }

    private static Query elkQueryOf(SearchBookingCatalogQuery query) {
        final var origin = query.filter().origin();
        final var destination = query.filter().destination();
        final var queries = asList(
                availableOnly(),
                originatedOn(origin),
                destinationOn(destination));
        return FlightElkQueryBuilder.build(queries);
    }

    private static SearchFlightsCatalogDto toDto(FlightDocument it) {
        return new SearchFlightsCatalogDto(it.id, new ItineraryDto(it.origin, it.destination),
                                           new ScheduleDto(it.boardingAt, it.landingAt));
    }

    @Override
    public GetFlightCatalogDto getFlight(String id) {
        return null;
    }
}
