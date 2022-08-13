package com.example.entities;

public class Task {
    private final String taskName;
    private String taskStatus;

    public Task(String taskName) {
        this.taskName = taskName;
        this.taskStatus = "CREATED";
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public String changeStatus(String newStatus) {
        String res = "ERROR";
        if (this.taskStatus.equals("CREATED") && newStatus.equals("CLOSE_TASK")) {
            this.taskStatus = "CLOSED";
            res = "CLOSED";
        } else if (this.taskStatus.equals("CLOSED") && newStatus.equals("DELETE_TASK")) {
            this.taskStatus = "DELETED";
            res = "DELETED";
        } else if (this.taskStatus.equals("CLOSED") && newStatus.equals("REOPEN_TASK")) {
            this.taskStatus = "REOPENED";
            res = "REOPENED";
        } else if (this.taskStatus.equals("REOPENED") && newStatus.equals("CLOSE_TASK")) {
            this.taskStatus = "CLOSED";
            res = "CLOSED";
        }
        return res;
    }
}