package com.example.demo.exceptions;

public class ViolationOfUniquenessException extends RuntimeException {
    public ViolationOfUniquenessException(String errorMessage) {
        super(errorMessage);
    }
}
