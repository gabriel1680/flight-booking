package org.gbl.admin.out.db.postgres;

import org.gbl.admin.app.domain.Flight;
import org.gbl.admin.app.service.FlightRepository;
import org.gbl.admin.out.db.postgres.mapper.PostgresFlightMapper;
import org.gbl.kernel.domain.Identity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PostgresFlightRepository implements FlightRepository {

    private final FlightPostgresJPARepository jpaRepository;

    private final PostgresFlightMapper mapper;

    public PostgresFlightRepository(FlightPostgresJPARepository jpaRepository) {
        this.jpaRepository = jpaRepository;
        this.mapper = new PostgresFlightMapper();
    }

    @Override
    public void save(Flight flight) {
        jpaRepository.save(mapper.toModel(flight));
    }

    @Override
    public Optional<Flight> findById(Identity flightId) {
        return jpaRepository.findById(flightId.uuid()).map(mapper::toDomain);
    }
}
