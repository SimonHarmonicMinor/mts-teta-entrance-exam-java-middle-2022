package com.example.demo.helpers;

import com.example.demo.models.Task;

public class TaskInfo {
    private final String User;
    private final TaskState State;

    public TaskInfo(Task task) {
        this.User = task.getUser();
        this.State = task.getState();
    }

    public String getUser() {
        return User;
    }

    public TaskState getState() {
        return State;
    }
}
