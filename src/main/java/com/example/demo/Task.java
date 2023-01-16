package com.example.demo;

public class Task {
    private final String userName;
    private final String taskName;
    private TaskStatus taskStatus;

    public Task(String userName, String taskName, TaskStatus taskStatus) {
        this.userName = userName;
        this.taskName = taskName;
        this.taskStatus = taskStatus;
    }

    public String getUserName() {
        return userName;
    }

    public String getTaskName() {
        return taskName;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
