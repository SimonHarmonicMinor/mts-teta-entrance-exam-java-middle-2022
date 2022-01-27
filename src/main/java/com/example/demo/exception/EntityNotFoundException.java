package com.example.demo.exception;

import java.util.function.Supplier;

public class EntityNotFoundException extends UserActionException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
