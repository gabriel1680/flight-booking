package org.gbl.catalog.kafka;

public record Payload<T>(Operation operation, T before) {}
