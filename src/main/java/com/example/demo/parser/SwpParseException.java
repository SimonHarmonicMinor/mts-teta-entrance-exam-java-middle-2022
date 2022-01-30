package com.example.demo.parser;

public class SwpParseException extends Exception {
    public SwpParseException() {
    }

    public SwpParseException(String message) {
        super(message);
    }

    public SwpParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public SwpParseException(Throwable cause) {
        super(cause);
    }

    public SwpParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
