package com.example.demo.models;

import com.example.demo.enums.TaskStatus;

public class Task {

    private Long id;
    private final String name;
    private final String user;
    private TaskStatus status;

    public Task(String user, String name) {
        this.user = user;
        this.name = name;
        this.status = TaskStatus.CREATED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
