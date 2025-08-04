package org.gbl.kernel.infra.rabbitmq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gbl.kernel.application.Event;
import org.gbl.kernel.application.EventDispatcher;
import org.gbl.kernel.infra.rabbitmq.config.RabbitMQProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RabbitMQEventBus implements EventDispatcher {

    private static final Logger LOG = LogManager.getLogger(RabbitMQEventBus.class);

    private final Map<String, RabbitMQProperties> rabbitConfig;

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQEventBus(Map<String, RabbitMQProperties> rabbitConfig,
                            RabbitTemplate rabbitTemplate) {
        this.rabbitConfig = rabbitConfig;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void dispatch(Event event) {
        final var config = rabbitConfig.get(event.name());
        if (config == null) {
            LOG.error("Invalid event name or rabbit config. [eventName:{}] " +
                              "[rabbitConfig:{}]", event.name(), rabbitConfig);
            return;
        }
        rabbitTemplate.convertAndSend(config.getExchange(), config.getRoutingKey(), event);
    }
}
