package org.gbl.catalog.in.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka")
public record KafkaProperties(String bootstrapServers, int poolTimeout, boolean autoCreateTopics) {}
