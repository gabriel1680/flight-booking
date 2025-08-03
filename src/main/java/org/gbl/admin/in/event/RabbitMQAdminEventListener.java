package org.gbl.admin.in.event;

import org.gbl.booking.event.BookingCreated;
import org.gbl.kernel.infra.rabbitmq.RabbitMQEventListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQAdminEventListener extends RabbitMQEventListener {

    private final BookingCreatedHandler handler;

    public RabbitMQAdminEventListener(BookingCreatedHandler handler) {
        this.handler = handler;
    }

    @RabbitListener(queues = {"${rabbit.queues.booking.created.name}"})
    public void listenToBookingCreated(@Payload String payload) {
        createConsumerFor(payload, BookingCreated.class).accept(handler::handle);
    }
}
