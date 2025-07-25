package org.gbl.catalog.dto;

import java.time.Instant;
import java.util.Collection;

public class CatalogDto {
    public record SearchFlightsCatalogQuery(int page, int size, String order,
                                            SearchFilter filter) {}

    public record SearchFilter(String origin, String destination) {}

    public record SearchFlightsCatalogDto(String id, ItineraryDto itinerary,
                                          ScheduleDto schedule) {}

    public record ItineraryDto(String origin, String destination) {}

    public record ScheduleDto(Instant boardingAt, Instant landingAt) {}

    public record GetFlightCatalogDto(String id, ItineraryDto itinerary, ScheduleDto schedule,
                                      Collection<SeatDto> seats) {}

    public record SeatDto(String id, String number, double price, boolean isAvailable) {}

    public record Pagination(int page, int total, int size,
                             Collection<SearchFlightsCatalogDto> documents) {}
}
