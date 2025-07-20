package org.gbl.booking.repository;

import org.gbl.booking.domain.Booking;

public interface BookingRepository {
    void save(Booking booking);
}
