package org.gbl.admin.app.domain;

import org.gbl.admin.app.domain.Schedule.InvalidScheduleException;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScheduleTest {

    private static final Instant NOW = Instant.now();

    @Test
    void nullValues() {
        assertThrows(InvalidScheduleException.class, () -> new Schedule(null, NOW));
        assertThrows(InvalidScheduleException.class, () -> new Schedule(NOW, null));
    }

    @Test
    void boardingAfterLanding() {
        var boarding = NOW;
        var landing = boarding.minus(3, ChronoUnit.HOURS);
        assertThrows(InvalidScheduleException.class, () -> new Schedule(boarding, landing));
    }

    @Test
    void boardingBeforeLanding() {
        var boarding = NOW;
        var landing = boarding.plus(3, ChronoUnit.HOURS);
        assertDoesNotThrow(() -> new Schedule(boarding, landing));
    }
}