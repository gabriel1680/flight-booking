package org.gbl.kernel.application;

public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message, null, false, false);
    }
}
