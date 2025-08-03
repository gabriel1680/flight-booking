package org.gbl.admin.in.config;

import org.gbl.admin.FlightAdminApi;
import org.gbl.admin.app.FlightAdminFacade;
import org.gbl.admin.app.domain.Flight;
import org.gbl.kernel.application.EventDispatcher;
import org.gbl.admin.app.service.FlightQueryService;
import org.gbl.admin.app.service.FlightRepository;
import org.gbl.admin.out.db.memory.InMemoryFlightRepository;
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
