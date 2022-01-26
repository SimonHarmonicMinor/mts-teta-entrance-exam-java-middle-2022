package com.example.demo.model;

import com.example.demo.enums.TaskStatus;

public class Task {
    private final String name;
    private final String user;
    private TaskStatus status;

    public Task(String name, String user) {
        this.name = name;
        this.user = user;
        this.status = TaskStatus.CREATED;
    }

    public String getName() {
        return name;
    }

    public String getUser() {
        return user;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
