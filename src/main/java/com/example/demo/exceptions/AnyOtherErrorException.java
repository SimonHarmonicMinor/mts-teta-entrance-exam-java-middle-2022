package com.example.demo.exceptions;

public class AnyOtherErrorException extends RuntimeException {

    public AnyOtherErrorException(String errorMessage) {
        super(errorMessage);
    }
}
