package org.gbl.catalog;

import org.gbl.catalog.dto.CatalogDto.GetFlightCatalogDto;
import org.gbl.catalog.dto.CatalogDto.Pagination;
import org.gbl.catalog.dto.CatalogDto.SearchFlightsCatalogDto;
import org.gbl.catalog.dto.CatalogDto.SearchFlightsCatalogQuery;
import org.gbl.catalog.service.CatalogCommandService;
import org.gbl.catalog.service.CatalogCommandService.FlightCreatedDto;
import org.gbl.catalog.service.CatalogQueryService;
import org.springframework.stereotype.Service;

@Service
public class CatalogApiImpl implements CatalogApi {

    private final CatalogQueryService queryService;
    private final CatalogCommandService commandService;

    public CatalogApiImpl(CatalogQueryService queryService, CatalogCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    @Override
    public Pagination<SearchFlightsCatalogDto> searchFlights(SearchFlightsCatalogQuery query) {
        return queryService.searchFlights(query);
    }

    @Override
    public GetFlightCatalogDto getFlight(String id) {
        return queryService.getFlight(id);
    }

    @Override
    public void crate(FlightCreatedDto dto) {
        commandService.create(dto);
    }

    @Override
    public void delete(String flightId) {
        commandService.delete(flightId);
    }
}
