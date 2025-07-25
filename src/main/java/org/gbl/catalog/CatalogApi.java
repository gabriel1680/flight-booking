package org.gbl.catalog;

import org.gbl.catalog.dto.CatalogDto.GetFlightCatalogDto;
import org.gbl.catalog.dto.CatalogDto.Pagination;
import org.gbl.catalog.dto.CatalogDto.SearchBookingCatalogQuery;

public interface CatalogApi {
    Pagination searchFlights(SearchBookingCatalogQuery query);

    GetFlightCatalogDto getFlight(String id);
}
