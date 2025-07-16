package org.gbl.flight_admin.in.http.controller;

import org.gbl.flight_admin.FlightAdminApi.AddFlightSeatRequest;
import org.gbl.flight_admin.FlightAdminApi.AddFlightSeatsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/flights/{flightId}/seats")
public class SeatsController extends AdminController {

    private String flightId;

    @SuppressWarnings("unused")
    @ModelAttribute
    public void bindPathVariable(@PathVariable String flightId) {
        this.flightId = flightId;
    }

    @PostMapping
    public ResponseEntity<Void> addSeats(@RequestBody AddSeatsDTO dto) {
        flightAdminApi.addSeats(new AddFlightSeatsRequest(flightId, dto.seats));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public record AddSeatsDTO(List<AddFlightSeatRequest> seats) {}
}
