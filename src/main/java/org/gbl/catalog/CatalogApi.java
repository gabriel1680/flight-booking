package org.gbl.catalog;

import org.gbl.catalog.dto.CatalogDto.GetFlightCatalogDto;
import org.gbl.catalog.dto.CatalogDto.Pagination;
import org.gbl.catalog.dto.CatalogDto.SearchFlightsCatalogDto;
import org.gbl.catalog.dto.CatalogDto.SearchFlightsCatalogQuery;
import org.gbl.catalog.service.CatalogCommandService.FlightCreatedDto;

public interface CatalogApi {
    Pagination<SearchFlightsCatalogDto> searchFlights(SearchFlightsCatalogQuery query);

    GetFlightCatalogDto getFlight(String id);

    void crate(FlightCreatedDto dto);

    void delete(String flightId);
}
