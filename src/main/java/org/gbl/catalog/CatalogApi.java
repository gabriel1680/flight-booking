package org.gbl.catalog;

import org.gbl.catalog.dto.CatalogDto.GetFlightCatalogDto;
import org.gbl.catalog.dto.CatalogDto.Pagination;
import org.gbl.catalog.dto.CatalogDto.SearchFlightsCatalogDto;
import org.gbl.catalog.dto.CatalogDto.SearchFlightsCatalogQuery;

public interface CatalogApi {
    Pagination<SearchFlightsCatalogDto> searchFlights(SearchFlightsCatalogQuery query);

    GetFlightCatalogDto getFlight(String id);
}
