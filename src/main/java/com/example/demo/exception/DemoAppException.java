package com.example.demo.exception;

public class DemoAppException extends Exception {

    private String errorResponse;

    public DemoAppException (String message, String errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }
}
