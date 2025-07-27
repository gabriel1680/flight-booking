package org.gbl.catalog.infra.kafka.connect;

public record MessageValue<T>(Payload<T> payload) {
}

