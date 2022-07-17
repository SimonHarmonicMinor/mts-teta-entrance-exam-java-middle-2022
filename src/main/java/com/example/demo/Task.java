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
    private TaskStatus status;
    public Task(String taskName, String ownerName) {
        this.taskName = taskName;
        this.ownerName = ownerName;
        this.status = TaskStatus.CREATED;
    }
    public TaskStatus getStatus() {
        return this.status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    
}

enum TaskStatus {
    CREATED,
    CLOSED,
    DELETED
}
