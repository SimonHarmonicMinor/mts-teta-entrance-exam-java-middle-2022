package com.example.demo.exception;

public class DemoException extends Exception{

    private String errorResponse;

    public DemoException(String message, String errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }

    public String getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
    }
}
