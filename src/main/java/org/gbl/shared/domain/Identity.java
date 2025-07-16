package org.gbl.shared.domain;

import java.util.Objects;
import java.util.UUID;

public class Identity {

    private final UUID value;

    private Identity(UUID value) {
        this.value = value;
    }

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

    public String value() {
        return value.toString();
    }

    public UUID uuid() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identity identity = (Identity) o;
        return Objects.equals(value, identity.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
