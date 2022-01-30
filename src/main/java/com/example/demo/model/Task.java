package com.example.demo.model;

public class Task {

    private String owner;
    private String name;
    private TaskStatus status;

    public Task(String owner, String name) {
        this.owner = owner;
        this.name = name;
        this.status = TaskStatus.CREATED;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return name;
    }
}
