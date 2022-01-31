package com.example.demo.exception;

public class TaskAlreadyExistsException extends Exception {
    public TaskAlreadyExistsException(String message) {
        super(message);
    }
}
