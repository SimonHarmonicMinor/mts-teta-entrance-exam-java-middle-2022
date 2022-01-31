package com.example.demo.model;

import com.example.demo.model.enums.Status;

import java.util.Objects;
import java.util.StringJoiner;

public class Task {

    private String userName;
    private String taskName;
    private Status status;

    private Task() {
    }

    public Task(String userName, String taskName) {
        this.userName = userName;
        this.taskName = taskName;
        status = Status.CREATED;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Task task = (Task) o;
        return Objects.equals(userName, task.userName)
                && Objects.equals(taskName, task.taskName)
                && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, taskName, status);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ")
                .add(taskName)
                .toString();
    }
}
