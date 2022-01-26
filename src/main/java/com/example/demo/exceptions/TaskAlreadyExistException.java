package com.example.demo.exceptions;

import com.example.demo.model.Task;

public class TaskAlreadyExistException extends AppException{
    public TaskAlreadyExistException(String message) {
        super(message);
    }

    public TaskAlreadyExistException(Task task) {
        super(task.getName());
    }
}
