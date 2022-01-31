package com.example.demo.model;

public class Task {
    private String name;
    private User user;
    private TaskStatus status;

    public Task(String name, User user) {
        this.name = name;
        this.user = user;
        this.status = TaskStatus.CREATED;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void close() {
        this.status = TaskStatus.CLOSED;
    }

    public void open() {
        this.status = TaskStatus.CREATED;
    }

    public void delete() {
        this.status = TaskStatus.DELETED;
    }

    @Override
    public String toString() {
        return "["
                + name + ", "
                + user.getName() + ", "
                + status +
                ']';
    }
}
