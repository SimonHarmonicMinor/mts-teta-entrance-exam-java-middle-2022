package com.example.demo.requests.exceptions;

public class RequestValidateException extends Exception {
    public RequestValidateException() {
        super("WRONG REQUEST FORMAT");
    }
}
