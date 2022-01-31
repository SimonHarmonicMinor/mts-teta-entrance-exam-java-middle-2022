package com.example.demo.model;

public class Task {

    private String name;

    private String userName;

    private TaskStatus status;

    public Task() {
    }

    public Task(String name, String userName, TaskStatus status) {
        this.name = name;
        this.userName = userName;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
