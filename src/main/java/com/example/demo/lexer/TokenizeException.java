package com.example.demo.lexer;

public class TokenizeException extends Exception {
    public TokenizeException() {
    }

    public TokenizeException(String message) {
        super(message);
    }

    public TokenizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenizeException(Throwable cause) {
        super(cause);
    }

    public TokenizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
