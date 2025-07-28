package org.gbl.admin.app.domain;

import org.gbl.admin.app.domain.Availability.SeatAlreadyTakenException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AvailabilityTest {

    @Test
    void takeAnUnavailableSeat() {
        var seatState = Availability.UNAVAILABLE;
        assertThrows(SeatAlreadyTakenException.class, seatState::take);
    }

    @Test
    void takeAnAvailableSeat() {
        var seatState = Availability.AVAILABLE;
        var otherState = assertDoesNotThrow(seatState::take);
        assertEquals(Availability.UNAVAILABLE, otherState);
    }
}