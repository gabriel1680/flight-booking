package org.gbl.booking.config;

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
public class BookingAmqpConfiguration {

    public static final String TOPIC_EXCHANGE = "booking-exchange";
    private static final String ROUTING_KEY = "booking.created";

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(ROUTING_KEY)
                .noargs();
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.directExchange(TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public Queue queue() {
        return QueueBuilder
                .durable("booking.created.queue")
                .expires((int) Duration.of(1, ChronoUnit.HOURS).get(ChronoUnit.MILLIS))
                .deadLetterExchange("booking.created.queue.dlq")
                .exclusive()
                .build();
    }
}
