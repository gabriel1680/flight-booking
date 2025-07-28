package org.gbl.admin.app.domain;

import org.gbl.admin.app.domain.SeatNumber.InvalidSeatNumberException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SeatNumberTest {

    @ParameterizedTest
    @NullAndEmptySource
    void emptyOrNull(String value) {
        assertThrows(InvalidSeatNumberException.class, () -> new SeatNumber(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345", "1234567", "12345A", "ABC456", "123-567"})
    void invalidFormat(String value) {
        assertThrows(InvalidSeatNumberException.class, () -> new SeatNumber(value));
    }

    @Test
    void validFormat() {
        final var seatNumber = assertDoesNotThrow(() -> new SeatNumber("123456"));
        assertEquals("123456", seatNumber.value());
    }
}