package org.gbl.catalog.infra.kafka.connect;

public record Payload<T>(Operation operation, T before) {}
