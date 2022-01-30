package com.example.demo.requests.exceptions;

public class AcessDeniedException extends Exception {
    public AcessDeniedException() {
        super("ACCESS_DENIED");
    }
}
