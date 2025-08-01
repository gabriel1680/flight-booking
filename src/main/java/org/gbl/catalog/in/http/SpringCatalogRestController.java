package org.gbl.catalog.in.http;

import org.gbl.catalog.CatalogApi;
import org.gbl.catalog.CatalogDto.GetFlightCatalogDto;
import org.gbl.catalog.CatalogDto.Pagination;
import org.gbl.catalog.CatalogDto.SearchFilter;
import org.gbl.catalog.CatalogDto.SearchFlightsCatalogDto;
import org.gbl.catalog.CatalogDto.SearchFlightsCatalogQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
public class SpringCatalogRestController {

    private final CatalogApi catalogApi;

    public SpringCatalogRestController(CatalogApi catalogApi) {
        this.catalogApi = catalogApi;
    }

    @GetMapping
    public ResponseEntity<Pagination<SearchFlightsCatalogDto>> searchForFlights(
            @Param("origin") String origin,
            @Param("destination") String destination,
            Pageable pageable) {
        final var query = new SearchFlightsCatalogQuery(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().toString(),
                new SearchFilter(origin, destination));
        return ResponseEntity.ok(catalogApi.searchFlights(query));
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<GetFlightCatalogDto> getFlightOf(
            @PathVariable("flightId") String flightId) {
        return ResponseEntity.ok(catalogApi.getFlight(flightId));
    }
}
