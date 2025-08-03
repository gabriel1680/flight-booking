package org.gbl.kernel.infra.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Configuration
public class RabbitMQAutoBindingConfig {

    private final Map<String, RabbitMQProperties> propertiesMap;

    public RabbitMQAutoBindingConfig(Map<String, RabbitMQProperties> propertiesMap) {
        this.propertiesMap = propertiesMap;
    }

    @Bean
    public List<Declarable> rabbitDeclarables() {
        return propertiesMap.values().stream()
                .flatMap(RabbitMQAutoBindingConfig::createDeclarablesStream)
                .toList();
    }

    private static Stream<Declarable> createDeclarablesStream(RabbitMQProperties props) {
        DirectExchange exchange = ExchangeBuilder
                .directExchange(props.exchange())
                .durable(true)
                .build();

        Queue queue = QueueBuilder
                .durable(props.queue())
                .ttl(props.ttl())
                .deadLetterExchange(props.dlx())
                .deadLetterRoutingKey(props.dlqRoutingKey())
                .build();

        Binding binding = BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(props.routingKey());

        DirectExchange dlx = ExchangeBuilder
                .directExchange(props.dlx())
                .durable(true)
                .build();

        Queue dlq = QueueBuilder
                .durable(props.dlx())
                .build();

        Binding dlqBinding = BindingBuilder
                .bind(dlq)
                .to(dlx)
                .with(props.dlqRoutingKey());

        return Stream.of(exchange, queue, binding, dlx, dlq, dlqBinding);
    }
}
