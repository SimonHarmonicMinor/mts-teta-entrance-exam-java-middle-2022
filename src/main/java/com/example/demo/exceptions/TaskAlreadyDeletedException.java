package com.example.demo.exceptions;

import com.example.demo.model.Task;

public class TaskAlreadyDeletedException extends AppException {
    public TaskAlreadyDeletedException(String message) {
        super(message);
    }

    public TaskAlreadyDeletedException(Task task) {
        super(task.getName());
    }
}
