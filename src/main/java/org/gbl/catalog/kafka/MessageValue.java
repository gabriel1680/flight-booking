package org.gbl.catalog.kafka;

public record MessageValue<T>(Payload<T> payload) {
}

