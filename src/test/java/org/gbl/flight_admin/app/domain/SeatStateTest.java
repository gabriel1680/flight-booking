package org.gbl.flight_admin.app.domain;

import org.gbl.flight_admin.app.domain.SeatState.SeatAlreadyTakenException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SeatStateTest {

    @Test
    void takeAnUnavailableSeat() {
        var seatState = SeatState.UNAVAILABLE;
        assertThrows(SeatAlreadyTakenException.class, seatState::take);
    }

    @Test
    void takeAnAvailableSeat() {
        var seatState = SeatState.AVAILABLE;
        var otherState = assertDoesNotThrow(seatState::take);
        assertEquals(SeatState.UNAVAILABLE, otherState);
    }
}