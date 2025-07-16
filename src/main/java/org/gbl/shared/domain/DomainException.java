package org.gbl.shared.domain;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message, null, false, false);
    }
}
