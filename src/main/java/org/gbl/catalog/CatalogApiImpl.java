package org.gbl.catalog;

import org.gbl.catalog.CatalogDto.GetFlightCatalogDto;
import org.gbl.catalog.CatalogDto.Pagination;
import org.gbl.catalog.CatalogDto.SearchFlightsCatalogDto;
import org.gbl.catalog.CatalogDto.SearchFlightsCatalogQuery;
import org.gbl.catalog.service.CatalogCommandService;
import org.gbl.catalog.service.CatalogCommandService.CreateFlightCommand;
import org.gbl.catalog.service.CatalogCommandService.DeleteFlightCommand;
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
    public void createFlight(CreateFlightCommand dto) {
        commandService.handle(dto);
    }

    @Override
    public void deleteFlightFor(String flightId) {
        commandService.handle(new DeleteFlightCommand(flightId));
    }
}
