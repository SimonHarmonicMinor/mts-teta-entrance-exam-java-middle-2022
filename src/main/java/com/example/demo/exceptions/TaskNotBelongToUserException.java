package com.example.demo.exceptions;

public class TaskNotBelongToUserException  extends AppException{
    public TaskNotBelongToUserException(String message) {
        super(message);
    }
}
