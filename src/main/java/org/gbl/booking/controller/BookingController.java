package org.gbl.booking.controller;

import org.gbl.booking.BookingApi;
import org.gbl.booking.BookingApi.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingApi bookingApi;

    public BookingController(BookingApi bookingApi) {
        this.bookingApi = bookingApi;
    }

    @PostMapping("/book")
    public ResponseEntity<Void> book(@RequestBody BookRequest request) {
        bookingApi.book(request);
        return ResponseEntity.ok().build();
    }
}
