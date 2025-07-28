package org.gbl.catalog.app.handler;

import org.gbl.catalog.CatalogDto.FlightDto;
import org.gbl.catalog.app.handler.CatalogCommandHandler.CreateFlightCommand;
import org.mapstruct.Mapper;

@Mapper
public interface FlightDtoMapper {
    CreateFlightCommand toCommand(FlightDto dto);
}
