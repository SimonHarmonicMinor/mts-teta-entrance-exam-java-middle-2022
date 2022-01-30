package com.example.demo.exception;

public class ServiceException extends RuntimeException {
    private final ErrorType type;

    public ServiceException(ErrorType type) {
        super();
        this.type = type;
    }

    public ServiceException() {
        super();
        this.type = ErrorType.COMMON;
    }

    public ErrorType getType() {
        return type;
    }

    public enum ErrorType {
        WRONG_FORMAT,
        ACCESS_DENIED,
        COMMON
    }
}
