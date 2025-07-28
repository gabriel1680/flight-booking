package org.gbl.catalog.app.mapper;

import org.gbl.catalog.CatalogDto.FlightDto;
import org.gbl.catalog.app.service.CatalogCommandService.CreateFlightCommand;
import org.mapstruct.Mapper;

@Mapper
public interface FlightDtoMapper {
    CreateFlightCommand toCommand(FlightDto dto);
}
