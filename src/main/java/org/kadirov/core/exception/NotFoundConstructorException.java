package org.kadirov.core.exception;

public class NotFoundConstructorException extends RuntimeException {
    public NotFoundConstructorException(String message) {
        super(message);
    }

    public NotFoundConstructorException(String message, Throwable cause) {
        super(message, cause);
    }
}
