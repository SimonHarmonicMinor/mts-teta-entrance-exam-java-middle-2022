package com.example.demo.exception;

public class TransitionNotAllowedException extends WorkflowException {
    public TransitionNotAllowedException(String message) {
        super(message);
    }
}
