package org.kadirov.core.exception;

public class UnDefinedAutowiredException extends RuntimeException {
    public UnDefinedAutowiredException(String message) {
        super(message);
    }

    public UnDefinedAutowiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
