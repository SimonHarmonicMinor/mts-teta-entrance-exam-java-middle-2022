package com.costa.util.config.exception;

public class ConfigLoadException extends RuntimeException {
    public ConfigLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigLoadException(String message) {
        super(message);
    }

    public ConfigLoadException() {
        super();
    }
}
