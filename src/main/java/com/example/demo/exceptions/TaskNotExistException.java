package com.example.demo.exceptions;

public class TaskNotExistException extends AppException{
    public TaskNotExistException(String message) {
        super(message);
    }

    public TaskNotExistException() {
    }
}
