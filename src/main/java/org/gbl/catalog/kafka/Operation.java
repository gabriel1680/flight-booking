package org.gbl.catalog.kafka;

public enum Operation {
    CREATE("c"),
    DELETE("d");

    private final String value;

    Operation(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static Operation of(String s) {
        for (var operation : values()) {
            if (operation.value.equals(s)) {
                return operation;
            }
        }
        throw new IllegalArgumentException("Invalid operation of %s".formatted(s));
    }
}
