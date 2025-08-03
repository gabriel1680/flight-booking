package org.gbl.kernel.infra.rabbitmq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class RabbitMQPropertiesConfiguration {

    @Bean("booking-created")
    @ConfigurationProperties(prefix = "rabbit.queues.booking-created")
    public RabbitMQProperties bookingCreated() {
        return new RabbitMQProperties();
    }

    @Bean("booking-confirmed")
    @ConfigurationProperties(prefix = "rabbit.queues.booking-confirmed")
    public RabbitMQProperties bookingConfirmed() {
        return new RabbitMQProperties();
    }

    @Bean("booking-failed")
    @ConfigurationProperties(prefix = "rabbit.queues.booking-failed")
    public RabbitMQProperties bookingFailed() {
        return new RabbitMQProperties();
    }
}
