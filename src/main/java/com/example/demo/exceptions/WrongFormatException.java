package com.example.demo.exceptions;

public class WrongFormatException extends RuntimeException {
    public WrongFormatException(String errorMessage) {
        super(errorMessage);
    }
}
