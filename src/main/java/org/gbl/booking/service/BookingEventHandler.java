package org.gbl.booking.service;

import org.gbl.admin.app.event.BookingConfirmed;
import org.gbl.admin.app.event.BookingFailed;
import org.gbl.booking.domain.Booking;
import org.gbl.booking.domain.BookingRepository;
import org.gbl.booking.service.BookingService.BookingNotFoundException;
import org.gbl.kernel.application.EventHandler;
import org.gbl.kernel.domain.Identity;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class BookingEventHandler implements EventHandler<BookingConfirmed> {

    private final BookingRepository bookingRepository;

    public BookingEventHandler(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @EventListener
    public void handle(BookingConfirmed event) {
        final var booking = getBookingFor(event.bookingId());
        booking.confirm();
        bookingRepository.save(booking);
    }

    @EventListener
    public void handle(BookingFailed event) {
        final var booking = getBookingFor(event.bookingId());
        booking.fail();
        bookingRepository.save(booking);
    }

    private Booking getBookingFor(String bookingId) {
        return bookingRepository.findById(Identity.of(bookingId))
                .orElseThrow(() -> new BookingNotFoundException(bookingId));
    }
}
