package com.example.demo.model;

public class Task {

    private final String name;

    private TaskState state;

    public Task(String name) {
        this.name = name;
        this.state = TaskState.CREATED;
    }

    public String getName() {
        return name;
    }

    public TaskState getState() {
        return state;
    }

    public boolean close() {
        if (!state.equals(TaskState.CREATED)) {
            return false;
        }
        state = TaskState.CLOSED;
        return true;
    }

    public boolean reopen() {
        if (!state.equals(TaskState.CLOSED)) {
            return false;
        }
        state = TaskState.CREATED;
        return true;
    }

}
