package org.gbl.catalog.in.kafka.util;

public record MessageValue<T>(Payload<T> payload) {
}

