package org.gbl.admin.in.http.controller;

import org.gbl.admin.FlightAdminApi.CreateFlightRequest;
import org.gbl.admin.FlightAdminApi.FlightQueryResponse;
import org.gbl.admin.FlightAdminApi.GetFlightRequest;
import org.gbl.admin.FlightAdminApi.GetFlightResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/admin/flights")
public class FlightController extends AdminController {

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
    public ResponseEntity<Collection<FlightQueryResponse>> getFlights() {
        return ResponseEntity.ok(flightAdminApi.getFlights());
    }
}
