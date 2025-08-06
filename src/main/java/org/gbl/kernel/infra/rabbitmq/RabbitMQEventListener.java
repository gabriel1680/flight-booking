package org.gbl.kernel.infra.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.LowerCamelCaseStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gbl.kernel.infra.jackson.JacksonJsonParser;

import java.util.function.Consumer;

public abstract class RabbitMQEventListener {

    protected static final Logger LOG = LogManager.getLogger(RabbitMQEventListener.class);
    // TODO: rethink this... not the best config, creates duplication in the codebase.
    private static final ObjectMapper mapper = JacksonJsonParser.mapper()
            .setPropertyNamingStrategy(new LowerCamelCaseStrategy());

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
        try {
            return mapper.readValue(payload, clazz);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
