package com.example.demo.model;

public class Task {
    private String name;
    private TaskState taskState;

    public Task(String name) {
        this.name = name;
        this.taskState = TaskState.CREATED;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    @Override
    public String toString() {
        return name;
    }
}
