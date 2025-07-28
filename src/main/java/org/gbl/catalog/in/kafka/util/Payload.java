package org.gbl.catalog.in.kafka.util;

public record Payload<T>(Operation operation, T before) {}
