package org.gbl.booking.repository;

import org.gbl.booking.repository.model.BookingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingJPARepository extends JpaRepository<BookingModel, UUID> {
    @Query(value = """
            SELECT EXISTS (
              SELECT 1
              FROM bookings b
              JOIN booking_seats bs ON bs.booking_id = b.id
              WHERE b.flight_id = :flightId
                AND b.status != 'FAILED'
                AND bs.seat_id IN (:seatIds)
            )
            """, nativeQuery = true)
    boolean bookingExistsFor(@Param("flightId") UUID flightId,
                             @Param("seatIds") List<UUID> seatIds);
}
