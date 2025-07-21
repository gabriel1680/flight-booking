package org.gbl.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingJPARepository extends JpaRepository<BookingModel, UUID> {
}
