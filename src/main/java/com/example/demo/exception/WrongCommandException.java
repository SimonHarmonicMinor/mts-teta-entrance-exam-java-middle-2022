package com.example.demo.exception;

public class WrongCommandException extends Exception {
    public WrongCommandException(String message) {
        super(message);
    }
}
