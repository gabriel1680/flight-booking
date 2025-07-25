package org.gbl.catalog.infra.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.gbl.catalog.CatalogApi;
import org.gbl.catalog.dto.CatalogDto.GetFlightCatalogDto;
import org.gbl.catalog.dto.CatalogDto.ItineraryDto;
import org.gbl.catalog.dto.CatalogDto.Pagination;
import org.gbl.catalog.dto.CatalogDto.ScheduleDto;
import org.gbl.catalog.dto.CatalogDto.SearchFlightsCatalogQuery;
import org.gbl.catalog.dto.CatalogDto.SearchFlightsCatalogDto;
import org.gbl.catalog.dto.CatalogDto.SeatDto;
import org.gbl.catalog.infra.elasticsearch.document.FlightDocument;
import org.gbl.catalog.infra.elasticsearch.document.SeatDocument;
import org.gbl.kernel.application.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchOperations;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.gbl.catalog.infra.elasticsearch.FlightElkQueryBuilder.availableOnly;
import static org.gbl.catalog.infra.elasticsearch.FlightElkQueryBuilder.destinationOn;
import static org.gbl.catalog.infra.elasticsearch.FlightElkQueryBuilder.originatedOn;

@Service
public class ElasticSearchCatalogApi implements CatalogApi {

    private final SearchOperations searchOperations;
    private final FlightElkRepository flightElkRepository;

    public ElasticSearchCatalogApi(SearchOperations searchOperations,
                                   FlightElkRepository flightElkRepository) {
        this.searchOperations = searchOperations;
        this.flightElkRepository = flightElkRepository;
    }

    @Override
    public Pagination searchFlights(SearchFlightsCatalogQuery query) {
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

    private static Query elkQueryOf(SearchFlightsCatalogQuery query) {
        final var origin = query.filter().origin();
        final var destination = query.filter().destination();
        final var queries = asList(
                availableOnly(),
                originatedOn(origin),
                destinationOn(destination));
        return FlightElkQueryBuilder.build(queries);
    }

    private static SearchFlightsCatalogDto toDto(FlightDocument it) {
        return new SearchFlightsCatalogDto(
                it.id,
                new ItineraryDto(it.origin, it.destination),
                new ScheduleDto(it.boardingAt, it.landingAt));
    }

    @Override
    public GetFlightCatalogDto getFlight(String id) {
        return flightElkRepository.findById(id)
                .map(ElasticSearchCatalogApi::toFlightDto)
                .orElseThrow(() -> new NotFoundException("Flight not found )="));
    }

    private static GetFlightCatalogDto toFlightDto(FlightDocument flight) {
        return new GetFlightCatalogDto(
                flight.id,
                new ItineraryDto(flight.origin, flight.destination),
                new ScheduleDto(flight.boardingAt, flight.landingAt),
                toSeatsDto(flight.seats));
    }

    private static List<SeatDto> toSeatsDto(Collection<SeatDocument> seats) {
        return seats.stream()
                .map(seat -> new SeatDto(seat.id, seat.number, seat.price, seat.availability == 1))
                .toList();
    }
}
