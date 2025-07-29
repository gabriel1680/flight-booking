package org.gbl.catalog.in.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
public record KafkaProperties(String bootstrapServers, int poolTimeout, boolean autoCreateTopics) {
}
