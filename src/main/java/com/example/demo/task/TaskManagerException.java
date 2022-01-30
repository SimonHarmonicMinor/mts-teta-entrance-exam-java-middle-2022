package com.example.demo.task;

import java.io.IOException;

/**
 * Create by fkshistero on 30.01.2022.
 */
public class TaskManagerException extends IOException {

    private final TaskManagerError error;
    public TaskManagerException(TaskManagerError error, String message) {
        super(message);
        this.error = error;
    }

    public TaskManagerError getError() {
        return error;
    }
}
