package org.gbl.admin.app.domain;

import org.gbl.admin.app.domain.Capacity.InvalidCapacityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CapacityTest {

    @Test
    void negative() {
        assertThrows(InvalidCapacityException.class, () -> new Capacity(-1));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 100})
    void zeroAndPositives(int capacity) {
        final var valueObject = assertDoesNotThrow(() -> new Capacity(capacity));
        assertEquals(capacity, valueObject.value());
    }
}