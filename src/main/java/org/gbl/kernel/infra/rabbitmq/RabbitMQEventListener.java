package org.gbl.kernel.infra.rabbitmq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gbl.kernel.infra.jackson.JacksonJsonParser;

import java.util.function.Consumer;

public abstract class RabbitMQEventListener {

    protected static final Logger LOG = LogManager.getLogger(RabbitMQEventListener.class);

    protected static <T> Consumer<Consumer<T>> createConsumerFor(String payload, Class<T> clazz) {
        return (consumer) -> {
            try {
                consumer.accept(parse(payload, clazz));
            } catch (Throwable t) {
                LOG.error("Failed to handle rabbitmq event", t);
            }
        };
    }

    private static <T> T parse(String payload, Class<T> clazz) {
        return JacksonJsonParser.parse(payload, clazz);
    }
}
