package com.example.demo.repositories.exceptions;

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException() {
        super("TASK NOT FOUND");
    }
}
