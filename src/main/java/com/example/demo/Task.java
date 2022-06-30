package com.example.demo;

public class Task {
    
    private String taskName;
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    private String ownerName;
    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    private String status;
    public Task(String taskName, String ownerName) {
        this.taskName = taskName;
        this.ownerName = ownerName;
        this.status = "CREATED";
    }

    public Task(Task t) {
        this.taskName = t.getTaskName();
        this.ownerName = t.getOwnerName();
        this.status = t.getStatus();
    }

    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    
}
