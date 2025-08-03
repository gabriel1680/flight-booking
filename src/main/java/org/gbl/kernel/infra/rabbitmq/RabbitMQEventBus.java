package org.gbl.kernel.infra.rabbitmq;

import org.gbl.kernel.application.Event;
import org.gbl.kernel.application.EventDispatcher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQEventBus implements EventDispatcher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQEventBus(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void dispatch(Event event) {
        rabbitTemplate.convertAndSend(event.name(), event);
    }
}
