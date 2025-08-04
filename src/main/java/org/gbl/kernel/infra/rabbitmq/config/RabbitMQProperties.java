package org.gbl.kernel.infra.rabbitmq.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMQProperties {
    private String exchange;
    private String queue;
    private String routingKey;
    private String dlx;
    private String dlqRoutingKey;
    private int ttl;
}