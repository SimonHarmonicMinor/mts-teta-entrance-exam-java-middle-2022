package com.example.demo.exception;

public class NonUniqTaskException extends UserActionException {
    public NonUniqTaskException(String message) {
        super(message);
    }
}
