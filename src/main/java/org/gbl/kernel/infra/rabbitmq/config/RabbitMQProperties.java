package org.gbl.kernel.infra.rabbitmq.config;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RabbitMQProperties {
    private String exchange;
    private String queue;
    private String routingKey;
    private String dlx;
    private String dlq;
    private String dlqRoutingKey;
    private int ttl;
}