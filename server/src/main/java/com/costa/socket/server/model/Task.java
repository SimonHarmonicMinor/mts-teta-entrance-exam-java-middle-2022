package com.costa.socket.server.model;

import java.util.Objects;

public class Task {
    private final String description;
    private final User user;
    private final TaskState state;

    public Task(String description, User user, TaskState state) {
        this.description = description;
        this.user = user;
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    public TaskState getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(description, task.description) && Objects.equals(user, task.user) && state == task.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, user, state);
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", user=" + user +
                ", state=" + state +
                '}';
    }
}
