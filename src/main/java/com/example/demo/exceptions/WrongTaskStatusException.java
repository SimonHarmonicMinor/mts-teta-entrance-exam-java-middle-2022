package com.example.demo.exceptions;

public class WrongTaskStatusException extends RuntimeException {
    public WrongTaskStatusException(String errorMessage) {
        super(errorMessage);
    }
}
