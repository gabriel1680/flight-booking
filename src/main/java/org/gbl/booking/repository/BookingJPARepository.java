package org.gbl.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingJPARepository extends JpaRepository<BookingModel, UUID> {
}
