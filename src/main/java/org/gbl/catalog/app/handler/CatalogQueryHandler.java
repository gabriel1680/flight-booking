package org.gbl.catalog.app.handler;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.gbl.catalog.CatalogDto.GetFlightCatalogDto;
import org.gbl.catalog.CatalogDto.ItineraryDto;
import org.gbl.catalog.CatalogDto.Pagination;
import org.gbl.catalog.CatalogDto.ScheduleDto;
import org.gbl.catalog.CatalogDto.SearchFlightsCatalogDto;
import org.gbl.catalog.CatalogDto.SearchFlightsCatalogQuery;
import org.gbl.catalog.CatalogDto.SeatDto;
import org.gbl.catalog.out.elasticsearch.FlightElkQueryBuilder;
import org.gbl.catalog.out.elasticsearch.FlightElkRepository;
import org.gbl.catalog.out.elasticsearch.document.FlightDocument;
import org.gbl.catalog.out.elasticsearch.document.SeatDocument;
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
public class CatalogQueryHandler {

    private final SearchOperations searchOperations;
    private final FlightElkRepository flightElkRepository;

    public CatalogQueryHandler(SearchOperations searchOperations,
                               FlightElkRepository flightElkRepository) {
        this.searchOperations = searchOperations;
        this.flightElkRepository = flightElkRepository;
    }

    public Pagination<SearchFlightsCatalogDto> handle(SearchFlightsCatalogQuery query) {
        final var searchQuery = NativeQuery.builder()
                .withQuery(elkQueryOf(query))
                .withPageable(PageRequest.of(query.page(), query.size()))
                .build();
        final var result = searchOperations.search(searchQuery, FlightDocument.class);
        final var documents = result.getSearchHits().stream()
                .map(SearchHit::getContent)
                .map(CatalogQueryHandler::toDto)
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
                FlightElkQueryBuilder.destinationTo(destination));
        return FlightElkQueryBuilder.build(queries);
    }

    private static SearchFlightsCatalogDto toDto(FlightDocument document) {
        return new SearchFlightsCatalogDto(
                document.id,
                new ItineraryDto(document.origin, document.destination),
                new ScheduleDto(document.boardingAt, document.landingAt));
    }

    public GetFlightCatalogDto handle(GetFlightQuery query) {
        return flightElkRepository.findById(query.id())
                .map(CatalogQueryHandler::toFlightDto)
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

    public record GetFlightQuery(String id) {}
}
