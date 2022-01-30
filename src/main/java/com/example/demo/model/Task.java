package com.example.demo.model;

public class Task {
    private final String name;
    private final String creator;
    private TaskState state = TaskState.CREATED;

    public Task(String name, String creator) {
        this.name = name;
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
    }

    public TaskState getState() {
        return state;
    }

    public void close() {
        if (state == TaskState.CREATED) {
            state = TaskState.CLOSED;
        } else if (state == TaskState.DELETED) {
            throw new IllegalStateException("you can't close a deleted task");
        }
    }

    public void delete() {
        state = TaskState.DELETED;
    }

    public void reOpen() {
        if (state == TaskState.CLOSED) {
            state = TaskState.CREATED;
        } else if (state == TaskState.DELETED) {
            throw new IllegalStateException("you can't re-open a deleted task");
        }
    }

    public static enum TaskState {
        CREATED, CLOSED, DELETED
    }
}

