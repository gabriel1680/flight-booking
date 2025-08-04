package org.gbl.kernel.infra.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Stream;

@Configuration
public class RabbitMQAutoBindingConfig {

    private final Map<String, RabbitMQProperties> propertiesMap;

    public RabbitMQAutoBindingConfig(Map<String, RabbitMQProperties> propertiesMap) {
        this.propertiesMap = propertiesMap;
    }

    @Bean
    public Declarables rabbitDeclarables() {
        final var list = propertiesMap.values().stream()
                .flatMap(RabbitMQAutoBindingConfig::createDeclarablesStream)
                .toList();
        return new Declarables(list);
    }

    private static Stream<Declarable> createDeclarablesStream(RabbitMQProperties props) {
        DirectExchange exchange = ExchangeBuilder
                .directExchange(props.getExchange())
                .durable(true)
                .build();

        Queue queue = QueueBuilder
                .durable(props.getQueue())
                .ttl(props.getTtl())
                .deadLetterExchange(props.getDlx())
                .deadLetterRoutingKey(props.getRoutingKey())
                .build();

        Binding binding = BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(props.getRoutingKey());

        DirectExchange dlx = ExchangeBuilder
                .directExchange(props.getDlx())
                .durable(true)
                .build();

        Queue dlq = QueueBuilder
                .durable(props.getDlq())
                .build();

        Binding dlqBinding = BindingBuilder
                .bind(dlq)
                .to(dlx)
                .with(props.getDlqRoutingKey());

        return Stream.of(exchange, queue, binding, dlx, dlq, dlqBinding);
    }
}
