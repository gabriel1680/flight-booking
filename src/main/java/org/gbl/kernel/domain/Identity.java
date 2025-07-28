package org.gbl.kernel.domain;

import java.util.UUID;

public record Identity(UUID uuid) {

    public static Identity nextId() {
        return new Identity(UUID.randomUUID());
    }

    public static Identity of(String aString) {
        try {
            return new Identity(UUID.fromString(aString));
        } catch (RuntimeException e) {
            throw new DomainException("Invalid id format", e);
        }
    }

    public static Identity of(UUID aUuid) {
        return new Identity(aUuid);
    }

    public String value() {
        return uuid.toString();
    }
}
