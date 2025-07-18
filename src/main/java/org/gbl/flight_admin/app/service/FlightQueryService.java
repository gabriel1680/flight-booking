package org.gbl.flight_admin.app.service;

import org.gbl.flight_admin.FlightAdminApi.FlightQueryResponse;

import java.util.List;

public interface FlightQueryService {
    List<FlightQueryResponse> searchFlights();
}
