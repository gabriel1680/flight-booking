package org.gbl.admin.app.service;

import org.gbl.admin.FlightAdminApi.FlightQueryResponse;

import java.util.List;

public interface FlightQueryService {
    List<FlightQueryResponse> searchFlights();
}
