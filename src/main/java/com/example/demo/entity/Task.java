package com.example.demo.entity;

/**
 * Сущность задача, содержащая имя задачи, статус и имя пользователя
 */

public class Task {

    private String name;
    private Status status;
    private String userName;

    public Task(String name, Status status, String userName) {
        this.name = name;
        this.status = status;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", userName='" + userName + '\'' +
                '}';
    }
}
