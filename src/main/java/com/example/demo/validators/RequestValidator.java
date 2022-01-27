package com.example.demo.validators;

public interface RequestValidator {
    boolean isValid(String request);

    boolean isInvalid(String request);
}
