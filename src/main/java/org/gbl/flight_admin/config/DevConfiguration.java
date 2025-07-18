package org.gbl.flight_admin.config;

import org.gbl.flight_admin.FlightAdminApi;
import org.gbl.flight_admin.FlightAdminFacade;
import org.gbl.flight_admin.app.domain.Flight;
import org.gbl.flight_admin.app.service.FlightQueryService;
import org.gbl.flight_admin.app.service.FlightRepository;
import org.gbl.flight_admin.out.memory.InMemoryFlightRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("development")
public class DevConfiguration {

    private final List<Flight> flights = new ArrayList<>();

    @Bean
    public FlightAdminApi flightAdminApi(FlightRepository flightRepository,
                                         FlightQueryService queryService) {
        return new FlightAdminFacade(flightRepository, queryService);
    }

    @Primary
    @Bean
    public FlightRepository flightRepository() {
        return new InMemoryFlightRepository(flights);
    }

    @Bean
    public FlightQueryService flightQueryService() {
        return new InMemoryFlightRepository(flights);
    }
}
