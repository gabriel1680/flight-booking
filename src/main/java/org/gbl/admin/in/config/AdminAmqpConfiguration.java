package org.gbl.admin.in.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class AdminAmqpConfiguration {

    public static final String TOPIC_EXCHANGE = "admin-exchange";
    private static final String BOOKING_CONFIRMED_KEY = "booking.confirmed";
    private static final String BOOKING_FAILED_KEY = "booking.failed";

    @Bean
    public Declarables bindings() {
        return new Declarables(
                bookingConfirmedQueue(),
                bookingFailedQueue(),
                exchange(),
                bindingBookingConfirmed(),
                bindingBookingFailed());
    }

    @Bean
    public Binding bindingBookingConfirmed() {
        return BindingBuilder
                .bind(bookingConfirmedQueue())
                .to(exchange())
                .with(BOOKING_CONFIRMED_KEY)
                .noargs();
    }

    @Bean
    public Binding bindingBookingFailed() {
        return BindingBuilder
                .bind(bookingFailedQueue())
                .to(exchange())
                .with(BOOKING_FAILED_KEY)
                .noargs();
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.directExchange(TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue bookingFailedQueue() {
        final var config = QueueConfig.of(BOOKING_FAILED_KEY);
        return QueueBuilder
                .durable(config.queue())
                .expires((int) Duration.of(1, ChronoUnit.HOURS).get(ChronoUnit.MILLIS))
                .deadLetterExchange(config.dlq())
                .exclusive()
                .build();
    }

    @Bean
    public Queue bookingConfirmedQueue() {
        final var config = QueueConfig.of(BOOKING_CONFIRMED_KEY);
        return QueueBuilder
                .durable(config.queue())
                .expires((int) Duration.of(1, ChronoUnit.HOURS).get(ChronoUnit.MILLIS))
                .deadLetterExchange(config.dlq())
                .exclusive()
                .build();
    }

    private record QueueConfig(String queue, String dlq) {
        static QueueConfig of(final String routingKey) {
            final var queue = "%s.queue".formatted(routingKey);
            final var dlq = "%s.dlq".formatted(queue);
            return new QueueConfig(queue, dlq);
        }
    }
}
