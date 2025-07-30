package org.gbl.catalog.in.kafka.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Payload<T>(
        @JsonProperty("op") Operation operation, T after) {}
