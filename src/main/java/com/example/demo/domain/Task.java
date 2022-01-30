package com.example.demo.domain;

import com.example.demo.helper.TaskStatus;

import java.util.Objects;

public class Task {
    private String name;
    private TaskStatus status;
    private String userName;

    public Task(String name, String userName) {
        this.name = name;
        this.userName = userName;
        this.status = TaskStatus.CREATED;
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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) &&
                status == task.status &&
                Objects.equals(userName, task.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, status, userName);
    }
}
