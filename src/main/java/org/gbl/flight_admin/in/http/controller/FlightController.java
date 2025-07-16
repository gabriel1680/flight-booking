package org.gbl.flight_admin.in.http.controller;

import org.gbl.flight_admin.FlightAdminApi.CreateFlightRequest;
import org.gbl.flight_admin.FlightAdminApi.FlightQueryResponse;
import org.gbl.flight_admin.FlightAdminApi.GetFlightRequest;
import org.gbl.flight_admin.FlightAdminApi.GetFlightResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public class FlightController extends AdminController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Void> createFlight(@RequestBody CreateFlightRequest request) {
        flightAdminApi.createFlight(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GetFlightResponse> getFlight(@PathVariable String id) {
        final var flightResponse = flightAdminApi.getFlight(new GetFlightRequest(id));
        return ResponseEntity.ok(flightResponse);
    }

    @GetMapping
    public ResponseEntity<List<FlightQueryResponse>> getFlights() {
        return ResponseEntity.ok(flightAdminApi.getFlights());
    }
}
