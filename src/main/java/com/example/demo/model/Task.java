package com.example.demo.model;

import com.example.demo.model.types.TaskStatus;

public class Task {

    private User owner;
    private String taskName;
    private TaskStatus status;

    public Task(User owner, String taskName, TaskStatus status) {
        this.owner = owner;
        this.taskName = taskName;
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }

    public String getTaskName() {
        return taskName;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "owner=" + owner +
                ", taskName='" + taskName + '\'' +
                ", status=" + status +
                '}';
    }
}
