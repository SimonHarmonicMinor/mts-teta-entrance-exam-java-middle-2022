package com.example.demo.exceptions;

public class UniquenessViolationException extends RuntimeException {
    public UniquenessViolationException(String errorMessage) {
        super(errorMessage);
    }
}
