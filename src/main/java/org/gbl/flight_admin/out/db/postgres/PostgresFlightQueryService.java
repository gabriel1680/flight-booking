package org.gbl.flight_admin.out.db.postgres;

import jakarta.persistence.EntityManager;
import org.gbl.flight_admin.FlightAdminApi.FlightQueryResponse;
import org.gbl.flight_admin.app.service.FlightQueryService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostgresFlightQueryService implements FlightQueryService {

    private final EntityManager entityManager;

    public PostgresFlightQueryService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<FlightQueryResponse> searchFlights() {
        String sql = """
                 SELECT
                    f.id, f.capacity,
                    COUNT(fs.id) AS total_seats,
                    COUNT(CASE WHEN fs.availability = 1 THEN 1 END) AS available_seats
                FROM
                    flights f
                LEFT JOIN
                    flight_seats fs ON fs.flight_id = f.id
                GROUP BY
                    f.id, f.capacity
                ORDER BY
                    f.id;""";
        return getRowsFor(sql)
                .stream()
                .map(PostgresFlightQueryService::toFlightQueryResponse)
                .toList();
    }

    @SuppressWarnings("unchecked")
    private List<Object[]> getRowsFor(String sql) {
        return entityManager
                .createNativeQuery(sql)
                .getResultList();
    }

    private static FlightQueryResponse toFlightQueryResponse(Object[] row) {
        return new FlightQueryResponse(
                row[0].toString(),
                ((Number) row[1]).intValue(),
                ((Number) row[2]).intValue(),
                ((Number) row[3]).intValue()
        );
    }
}
