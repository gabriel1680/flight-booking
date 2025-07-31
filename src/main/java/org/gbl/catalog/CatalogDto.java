package org.gbl.catalog;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

public class CatalogDto {
    public record SearchFlightsCatalogQuery(int page, int size, String order,
                                            SearchFilter filter) {}

    public record SearchFilter(String origin, String destination) {
        public static final SearchFilter EMPTY = new SearchFilter(null, null);
    }

    public record SearchFlightsCatalogDto(String id, ItineraryDto itinerary,
                                          ScheduleDto schedule) {}

    public record ItineraryDto(String origin, String destination) {}

    public record ScheduleDto(Instant boardingAt, Instant landingAt) {}

    public record GetFlightCatalogDto(String id, ItineraryDto itinerary, ScheduleDto schedule,
                                      Collection<SeatDto> seats) {}

    public record SeatDto(String id, String number, double price, boolean isAvailable) {}

    public record Pagination<T>(int page, int total, int size, Collection<T> documents) {}

    public record FlightDto(
            String id,
            String origin,
            String destination,
            Instant boardingAt,
            Instant landingAt,
            Collection<FlightSeatDto> seats,
            Instant createdAt,
            Instant updatedAt) {
        public FlightDto {
            if (seats == null) {
                seats = Collections.emptyList();
            }
        }
    }

    public record FlightSeatDto(String id, String number, int availability) {}
}
