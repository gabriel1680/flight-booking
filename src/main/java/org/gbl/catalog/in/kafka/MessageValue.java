package org.gbl.catalog.in.kafka;

public record MessageValue<T>(Payload<T> payload) {
}

