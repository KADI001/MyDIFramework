package org.kadirov.core.exception;

public class PackageDoesntExistException extends RuntimeException {

    public PackageDoesntExistException(String message) {
        super(message);
    }

    public PackageDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
