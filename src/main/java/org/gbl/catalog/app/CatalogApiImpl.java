package org.gbl.catalog.app;

import org.gbl.catalog.CatalogApi;
import org.gbl.catalog.CatalogDto.FlightDto;
import org.gbl.catalog.CatalogDto.FlightSeatDto;
import org.gbl.catalog.CatalogDto.GetFlightCatalogDto;
import org.gbl.catalog.CatalogDto.Pagination;
import org.gbl.catalog.CatalogDto.SearchFlightsCatalogDto;
import org.gbl.catalog.CatalogDto.SearchFlightsCatalogQuery;
import org.gbl.catalog.app.handler.CatalogCommandHandler;
import org.gbl.catalog.app.handler.CatalogCommandHandler.CreateFlightCommand;
import org.gbl.catalog.app.handler.CatalogCommandHandler.CreateSeatDto;
import org.gbl.catalog.app.handler.CatalogCommandHandler.DeleteFlightCommand;
import org.gbl.catalog.app.handler.CatalogQueryHandler;
import org.gbl.catalog.app.handler.CatalogQueryHandler.GetFlightQuery;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Service
public class CatalogApiImpl implements CatalogApi {

    private final FlightDtoMapper mapper;
    private final CatalogCommandHandler commandHandler;
    private final CatalogQueryHandler queryHandler;

    public CatalogApiImpl(
            CatalogQueryHandler queryHandler,
            CatalogCommandHandler commandHandler,
            FlightDtoMapper mapper) {
        this.queryHandler = queryHandler;
        this.commandHandler = commandHandler;
        this.mapper = mapper;
    }

    @Override
    public Pagination<SearchFlightsCatalogDto> searchFlights(SearchFlightsCatalogQuery query) {
        return queryHandler.handle(query);
    }

    @Override
    public GetFlightCatalogDto getFlight(String id) {
        return queryHandler.handle(new GetFlightQuery(id));
    }

    @Override
    public void createFlight(FlightDto dto) {
        commandHandler.handle(mapper.toCommand(dto));
    }

    @Override
    public void deleteFlightFor(String flightId) {
        commandHandler.handle(new DeleteFlightCommand(flightId));
    }

    @Mapper
    public interface FlightDtoMapper {
        CreateFlightCommand toCommand(FlightDto dto);

        CreateSeatDto toSeat(FlightSeatDto dto);
    }
}
