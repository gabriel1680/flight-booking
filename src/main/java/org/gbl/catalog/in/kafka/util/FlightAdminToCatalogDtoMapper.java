package org.gbl.catalog.in.kafka.util;

import org.gbl.admin.FlightAdminApi.GetFlightResponse;
import org.gbl.admin.FlightAdminApi.GetFlightSeatResponse;
import org.gbl.catalog.CatalogDto.FlightDto;
import org.gbl.catalog.CatalogDto.FlightSeatDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FlightAdminToCatalogDtoMapper {
    FlightAdminToCatalogDtoMapper INSTANCE =
            Mappers.getMapper(FlightAdminToCatalogDtoMapper.class);

    FlightDto toDto(GetFlightResponse flight);

    FlightSeatDto toSeatDto(GetFlightSeatResponse seat);
}
