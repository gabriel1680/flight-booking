package org.gbl.kernel.infra.spring.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class AmqpConfiguration {

    public static final String TOPIC_EXCHANGE = "booking-exchange";

    @Bean
    public Binding bindingBookingCreated() {
        return BindingBuilder
                .bind(bookingCreatedQueue())
                .to(exchange())
                .with("booking.created")
                .noargs();
    }

    @Bean
    public Binding bindingBookingConfirmed() {
        return BindingBuilder
                .bind(bookingConfirmedQueue())
                .to(exchange())
                .with("booking.confirmed")
                .noargs();
    }

    @Bean
    public Binding bindingBookingFailed() {
        return BindingBuilder
                .bind(bookingFailedQueue())
                .to(exchange())
                .with("booking.failed")
                .noargs();
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.directExchange(TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue bookingCreatedQueue() {
        return QueueBuilder
                .durable("booking.created.queue")
                .expires((int) Duration.of(1, ChronoUnit.HOURS).get(ChronoUnit.MILLIS))
                .deadLetterExchange("booking.created.queue.dlq")
                .exclusive()
                .build();
    }

    @Bean
    public Queue bookingFailedQueue() {
        return QueueBuilder
                .durable("booking.failed.queue")
                .expires((int) Duration.of(1, ChronoUnit.HOURS).get(ChronoUnit.MILLIS))
                .deadLetterExchange("booking.failed.queue.dql")
                .exclusive()
                .build();
    }

    @Bean
    public Queue bookingConfirmedQueue() {
        return QueueBuilder
                .durable("booking.confirmed.queue")
                .expires((int) Duration.of(1, ChronoUnit.HOURS).get(ChronoUnit.MILLIS))
                .deadLetterExchange("booking.confirmed.queue.dlq")
                .exclusive()
                .build();
    }

}
