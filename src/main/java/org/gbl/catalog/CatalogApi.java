package org.gbl.catalog;

import org.gbl.catalog.CatalogDto.GetFlightCatalogDto;
import org.gbl.catalog.CatalogDto.Pagination;
import org.gbl.catalog.CatalogDto.SearchFlightsCatalogDto;
import org.gbl.catalog.CatalogDto.SearchFlightsCatalogQuery;
import org.gbl.catalog.service.CatalogCommandService.FlightCreatedDto;

public interface CatalogApi {
    Pagination<SearchFlightsCatalogDto> searchFlights(SearchFlightsCatalogQuery query);

    GetFlightCatalogDto getFlight(String id);

    void crate(FlightCreatedDto dto);

    void delete(String flightId);
}
