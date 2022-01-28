package com.example.demo.entity;

/**
 * Сущность Task
 */

public class Task {

    private String name;
    private Status status;
    private String userName;

    public Task(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public Task() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
