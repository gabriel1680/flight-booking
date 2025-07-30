package org.gbl.catalog.in.kafka.util;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Operation {
    CREATE("c"),
    READ("r"),
    UPDATE("u"),
    DELETE("d");

    private final String value;

    Operation(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    @JsonCreator
    public static Operation of(String s) {
        for (var operation : values()) {
            if (operation.value.equals(s)) {
                return operation;
            }
        }
        throw new IllegalArgumentException("Invalid operation of %s".formatted(s));
    }
}
