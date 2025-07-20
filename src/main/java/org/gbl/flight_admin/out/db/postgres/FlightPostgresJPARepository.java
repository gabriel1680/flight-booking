package org.gbl.flight_admin.out.db.postgres;

import org.gbl.flight_admin.out.db.postgres.model.PostgresFlightModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FlightPostgresJPARepository extends JpaRepository<PostgresFlightModel, UUID> {
}
