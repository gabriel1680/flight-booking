package org.gbl.catalog.in.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gbl.catalog.CatalogApi;
import org.gbl.catalog.CatalogDto.FlightDto;
import org.gbl.catalog.in.kafka.util.JacksonJsonParser;
import org.gbl.catalog.in.kafka.util.MessageValue;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
public class KafkaConnectAdminListener {

    private static final Logger LOG = LogManager.getLogger(KafkaConnectAdminListener.class);

    private static final TypeReference<MessageValue<FlightDto>> VALUE_TYPE_REFERENCE
            = new TypeReference<>() {};

    private final CatalogApi catalogApi;

    public KafkaConnectAdminListener(CatalogApi catalogApi) {
        this.catalogApi = catalogApi;
    }

    @KafkaListener(
            concurrency = "${kafka.consumers.flights.concurrency}",
            containerFactory = "kafkaListenerContainerFactory",
            topics = "${kafka.consumers.flights.topics}",
            groupId = "${kafka.consumers.flights.group-id}",
            id = "${kafka.consumers.flights.id}",
            properties = {
                    "auto.offset.reset=${kafka.consumers.flights.auto-offset-reset}"
            }
    )
    @RetryableTopic(
            backoff = @Backoff(delay = 1_000, maxDelay = 10_000, multiplier = 2),
            attempts = "${kafka.consumers.flights.max-attempts}",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    public void onMessage(@Payload(required = false) final String payload,
                          final ConsumerRecordMetadata metadata) {
        if (payload == null) {
            logMessage(metadata, "EMPTY");
            return;
        }
        logMessage(metadata, payload);
        handle(payload);
    }

    private void handle(String payload) {
        final var messagePayload =
                JacksonJsonParser.parse(payload, VALUE_TYPE_REFERENCE).payload();
        final var operation = messagePayload.operation();
        final var flight = messagePayload.before();
        switch (operation) {
            case CREATE -> catalogApi.createFlight(flight);
            case DELETE -> catalogApi.deleteFlightFor(flight.id());
        }
    }

    private static void logMessage(ConsumerRecordMetadata metadata, String payload) {
        LOG.info("Message received with [topic:{}] [partition:{}] [offset:{}] [payload:{}]",
                 metadata.topic(), metadata.partition(), metadata.offset(), payload);
    }

    @DltHandler
    public void onDLTMessage(@Payload final String payload, final ConsumerRecordMetadata metadata) {
        LOG.error("DLT Message received with [topic:{}] [partition:{}] [offset:{}]: {}",
                  metadata.topic(), metadata.partition(), metadata.offset(), payload);
        // TODO: reprocess messages
    }
}
