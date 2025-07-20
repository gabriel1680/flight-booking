package org.gbl.booking.repository;

import org.gbl.booking.domain.Booking;

import java.util.Optional;

public interface BookingRepository {
    void save(Booking booking);

    Optional<Booking> findById(String bookingId);
}
