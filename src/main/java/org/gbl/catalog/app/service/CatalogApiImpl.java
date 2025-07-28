package org.gbl.catalog.app.service;

import org.gbl.catalog.CatalogApi;
import org.gbl.catalog.CatalogDto.FlightDto;
import org.gbl.catalog.CatalogDto.GetFlightCatalogDto;
import org.gbl.catalog.CatalogDto.Pagination;
import org.gbl.catalog.CatalogDto.SearchFlightsCatalogDto;
import org.gbl.catalog.CatalogDto.SearchFlightsCatalogQuery;
import org.gbl.catalog.app.mapper.FlightDtoMapper;
import org.gbl.catalog.app.service.CatalogCommandService.CreateFlightCommand;
import org.gbl.catalog.app.service.CatalogCommandService.DeleteFlightCommand;
import org.springframework.stereotype.Service;

@Service
public class CatalogApiImpl implements CatalogApi {

    private final CatalogQueryService queryService;
    private final CatalogCommandService commandService;
    private final FlightDtoMapper mapper;

    public CatalogApiImpl(
            CatalogQueryService queryService,
            CatalogCommandService commandService,
            FlightDtoMapper mapper) {
        this.queryService = queryService;
        this.commandService = commandService;
        this.mapper = mapper;
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
    public void createFlight(FlightDto dto) {
        commandService.handle(mapper.toCommand(dto));
    }

    @Override
    public void deleteFlightFor(String flightId) {
        commandService.handle(new DeleteFlightCommand(flightId));
    }
}
