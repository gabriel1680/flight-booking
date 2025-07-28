package org.gbl.catalog.service;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.gbl.catalog.CatalogDto.GetFlightCatalogDto;
import org.gbl.catalog.CatalogDto.ItineraryDto;
import org.gbl.catalog.CatalogDto.Pagination;
import org.gbl.catalog.CatalogDto.ScheduleDto;
import org.gbl.catalog.CatalogDto.SearchFlightsCatalogDto;
import org.gbl.catalog.CatalogDto.SearchFlightsCatalogQuery;
import org.gbl.catalog.CatalogDto.SeatDto;
import org.gbl.catalog.elasticsearch.FlightElkQueryBuilder;
import org.gbl.catalog.elasticsearch.FlightElkRepository;
import org.gbl.catalog.elasticsearch.document.FlightDocument;
import org.gbl.catalog.elasticsearch.document.SeatDocument;
import org.gbl.kernel.application.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchOperations;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;

@Service
public class CatalogQueryService {

    private final SearchOperations searchOperations;
    private final FlightElkRepository flightElkRepository;

    public CatalogQueryService(SearchOperations searchOperations,
                               FlightElkRepository flightElkRepository) {
        this.searchOperations = searchOperations;
        this.flightElkRepository = flightElkRepository;
    }

    public Pagination<SearchFlightsCatalogDto> searchFlights(SearchFlightsCatalogQuery query) {
        final var searchQuery = NativeQuery.builder()
                .withQuery(elkQueryOf(query))
                .withPageable(PageRequest.of(query.page(), query.size()))
                .build();
        final var result = searchOperations.search(searchQuery, FlightDocument.class);
        final var documents = result.getSearchHits().stream()
                .map(SearchHit::getContent)
                .map(CatalogQueryService::toDto)
                .toList();
        final var total = (int) result.getTotalHits();
        return new Pagination<>(query.page(), total, query.size(), documents);
    }

    private static Query elkQueryOf(SearchFlightsCatalogQuery query) {
        final var origin = query.filter().origin();
        final var destination = query.filter().destination();
        final var queries = asList(
                FlightElkQueryBuilder.availableOnly(),
                FlightElkQueryBuilder.originatedOn(origin),
                FlightElkQueryBuilder.destinationOn(destination));
        return FlightElkQueryBuilder.build(queries);
    }

    private static SearchFlightsCatalogDto toDto(FlightDocument it) {
        return new SearchFlightsCatalogDto(
                it.id,
                new ItineraryDto(it.origin, it.destination),
                new ScheduleDto(it.boardingAt, it.landingAt));
    }

    public GetFlightCatalogDto getFlight(String id) {
        return flightElkRepository.findById(id)
                .map(CatalogQueryService::toFlightDto)
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
