package org.gbl.shared.application;

public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message, null, false, false);
    }
}
