package org.gbl.catalog.in.kafka;

public record Payload<T>(Operation operation, T before) {}
