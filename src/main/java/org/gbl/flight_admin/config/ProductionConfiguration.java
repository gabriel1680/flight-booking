package org.gbl.flight_admin.config;

import org.gbl.flight_admin.FlightAdminApi;
import org.gbl.flight_admin.FlightAdminFacade;
import org.gbl.flight_admin.app.service.FlightRepository;
import org.gbl.flight_admin.out.postgres.FlightPostgresJPARepository;
import org.gbl.flight_admin.out.postgres.PostgresFlightRepository;
import org.gbl.flight_admin.out.postgres.mapper.PostgresFlightMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("production")
public class ProductionConfiguration {

    @Autowired
    private FlightPostgresJPARepository jpaRepository;

    @Bean
    public FlightAdminApi flightAdminApi(FlightRepository flightRepository) {
        return new FlightAdminFacade(flightRepository, jpaRepository);
    }

    @Bean
    public FlightRepository flightRepository(PostgresFlightMapper mapper) {
        return new PostgresFlightRepository(jpaRepository, mapper);
    }
}
