package org.gbl.admin.in.event;

import org.gbl.admin.FlightAdminApi;
import org.gbl.admin.FlightAdminApi.BookSeatsRequest;
import org.gbl.booking.event.BookingCreated;
import org.gbl.kernel.infra.rabbitmq.RabbitMQEventListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQAdminEventListener extends RabbitMQEventListener {

    private final FlightAdminApi flightAdminApi;

    public RabbitMQAdminEventListener(FlightAdminApi flightAdminApi) {
        this.flightAdminApi = flightAdminApi;
    }

    @RabbitListener(queues = {"${rabbit.queues.booking-created.queue}"})
    public void listenToBookingCreated(@Payload String payload) {
        createConsumerFor(payload, BookingCreated.class).accept(this::handle);
    }

    private void handle(BookingCreated event) {
        LOG.info("Processing BookingCreated event of bookingId: {}", event.bookingId());
        final var request = new BookSeatsRequest(event.bookingId(), event.flightId(),
                                                 event.seatIds());
        flightAdminApi.bookSeats(request);
    }
}
