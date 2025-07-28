package org.gbl.admin.app.domain;

import org.gbl.admin.app.domain.SeatType.InvalidSeatTypeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SeatTypeTest {

    @ParameterizedTest
    @NullAndEmptySource
    void empty(String type) {
        assertThrows(InvalidSeatTypeException.class, () -> SeatType.of(type));
    }

    @Test
    void invalidType() {
        assertThrows(InvalidSeatTypeException.class, () -> SeatType.of("avocado"));
    }

    @Test
    void validType() {
        final var executive = "executive";
        final var seatType = SeatType.of(executive);
        assertEquals(executive, seatType.value());
        assertEquals(executive, seatType.toString());
    }
}