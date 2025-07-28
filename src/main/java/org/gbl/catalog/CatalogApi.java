package org.gbl.catalog;

import org.gbl.catalog.CatalogDto.FlightDto;
import org.gbl.catalog.CatalogDto.GetFlightCatalogDto;
import org.gbl.catalog.CatalogDto.Pagination;
import org.gbl.catalog.CatalogDto.SearchFlightsCatalogDto;
import org.gbl.catalog.CatalogDto.SearchFlightsCatalogQuery;
import org.gbl.catalog.app.service.CatalogCommandService.CreateFlightCommand;

public interface CatalogApi {
    Pagination<SearchFlightsCatalogDto> searchFlights(SearchFlightsCatalogQuery query);

    GetFlightCatalogDto getFlight(String id);

    void createFlight(FlightDto dto);

    void deleteFlightFor(String flightId);
}
