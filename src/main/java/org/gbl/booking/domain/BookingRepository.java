package org.gbl.booking.domain;

import org.gbl.shared.domain.Identity;

import java.util.Optional;

public interface BookingRepository {
    void save(Booking booking);

    Optional<Booking> findById(Identity bookingId);
}
