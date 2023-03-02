package com.example.demo.exceptions;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String errorMessage) {
        super(errorMessage);
    }
}
