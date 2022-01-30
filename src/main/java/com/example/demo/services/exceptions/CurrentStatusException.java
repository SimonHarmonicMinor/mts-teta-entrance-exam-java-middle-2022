package com.example.demo.services.exceptions;

public class CurrentStatusException extends Exception {
    public CurrentStatusException() {
        super("CAN NOT CHANGE STATUS");
    }
}
