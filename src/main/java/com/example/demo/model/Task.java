package com.example.demo.model;

import com.example.demo.model.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.StringJoiner;

public class Task {
    String taskName;
    String userName;
    TaskStatus taskStatus;
    LocalDateTime createDate;

    public Task(String taskName, String userName) {
        this.taskName = taskName;
        this.userName = userName;
        this.taskStatus = TaskStatus.CREATED;
        this.createDate = LocalDateTime.now();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ")
                .add(taskName)
                .toString();
    }
}
