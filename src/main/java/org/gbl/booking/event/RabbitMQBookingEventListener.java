package org.gbl.booking.event;

import org.gbl.admin.app.event.BookingConfirmed;
import org.gbl.admin.app.event.BookingFailed;
import org.gbl.booking.BookingApi;
import org.gbl.kernel.infra.rabbitmq.RabbitMQEventListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQBookingEventListener extends RabbitMQEventListener {

    private final BookingApi bookingApi;

    public RabbitMQBookingEventListener(BookingApi bookingApi) {
        this.bookingApi = bookingApi;
    }

    @RabbitListener(queues = {"${rabbit.queues.booking-confirmed.queue}"})
    public void listenToConfirmed(@Payload String payload) {
        createConsumerFor(payload, BookingConfirmed.class)
                .accept(event -> bookingApi.confirmBooking(event.bookingId()));
    }

    @RabbitListener(queues = {"${rabbit.queues.booking-failed.queue}"})
    public void listenToFailed(@Payload String payload) {
        createConsumerFor(payload, BookingFailed.class)
                .accept(event -> bookingApi.failBooking(event.bookingId()));
    }
}
