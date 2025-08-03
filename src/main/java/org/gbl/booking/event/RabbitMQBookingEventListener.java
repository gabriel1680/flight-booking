package org.gbl.booking.event;

import org.gbl.admin.app.event.BookingConfirmed;
import org.gbl.admin.app.event.BookingFailed;
import org.gbl.booking.service.BookingEventHandler;
import org.gbl.kernel.infra.rabbitmq.RabbitMQEventListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQBookingEventListener extends RabbitMQEventListener {

    private final BookingEventHandler handler;

    public RabbitMQBookingEventListener(BookingEventHandler handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = {"${rabbit.queues.booking.confirmed.name"})
    public void listenToConfirmed(@Payload String payload) {
        createConsumerFor(payload, BookingConfirmed.class).accept(handler::handle);
    }

    @RabbitListener(queues = {"${rabbit.queues.booking.confirmed.name}"})
    public void listenToFailed(@Payload String payload) {
        createConsumerFor(payload, BookingFailed.class).accept(handler::handle);
    }
}
