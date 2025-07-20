package org.gbl.booking.controller;

import org.gbl.booking.BookingApi;
import org.gbl.booking.BookingApi.BookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("booking")
public class BookingController {

    @Autowired
    private BookingApi bookingApi;

    @PostMapping("/book")
    public ResponseEntity<Void> book(@RequestBody BookRequest request) {
        bookingApi.book(request);
        return ResponseEntity.ok().build();
    }
}
