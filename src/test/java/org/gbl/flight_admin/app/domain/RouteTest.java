package org.gbl.flight_admin.app.domain;

import org.gbl.flight_admin.app.domain.Route.InvalidRouteException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RouteTest {

    private static final String ORIGIN = "Budapest";
    private static final String DESTINATION = "Barcelona";

    @ParameterizedTest
    @NullAndEmptySource
    void nullOrEmptySource(String route) {
        assertThrows(InvalidRouteException.class, () -> new Route(route, DESTINATION));
        assertThrows(InvalidRouteException.class, () -> new Route(ORIGIN, route));
    }

    @Test
    void validRoute() {
        assertDoesNotThrow(() -> new Route(ORIGIN, DESTINATION));
    }
}