package org.gbl.booking.domain;

import org.gbl.booking.BookingApi.SeatReservationRequest;
import org.gbl.kernel.domain.Identity;

import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    void save(Booking booking);

    Optional<Booking> findById(Identity bookingId);

    boolean bookingExistsFor(String flightId, List<String> seatIds);
}
