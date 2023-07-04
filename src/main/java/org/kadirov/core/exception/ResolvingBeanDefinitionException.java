package org.kadirov.core.exception;

public class ResolvingBeanDefinitionException extends RuntimeException {
    public ResolvingBeanDefinitionException(String message) {
        super(message);
    }

    public ResolvingBeanDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }
}
