package org.gbl.flight_admin.in.http.config;

import org.gbl.flight_admin.FlightAdminApi;
import org.gbl.flight_admin.FlightAdminFacade;
import org.gbl.flight_admin.app.domain.Flight;
import org.gbl.flight_admin.app.service.EventDispatcher;
import org.gbl.flight_admin.app.service.FlightQueryService;
import org.gbl.flight_admin.app.service.FlightRepository;
import org.gbl.flight_admin.out.db.memory.InMemoryFlightRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CoreConfiguration {

    private final List<Flight> flights = new ArrayList<>();

    @Bean
    public FlightAdminApi flightAdminApi(FlightRepository flightRepository,
                                         FlightQueryService queryService,
                                         EventDispatcher eventDispatcher) {
        return new FlightAdminFacade(flightRepository, queryService, eventDispatcher);
    }

    @Primary
    @Profile("memory")
    @Bean
    public FlightRepository flightRepository() {
        return new InMemoryFlightRepository(flights);
    }

    @Profile("memory")
    @Bean
    public FlightQueryService flightQueryService() {
        return new InMemoryFlightRepository(flights);
    }
}
