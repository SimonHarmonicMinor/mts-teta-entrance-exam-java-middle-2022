package com.costa.socket.server.model;

import com.costa.socket.server.dao.TaskDao;

import java.util.Objects;

/**
 * Task model, used on the dao layer {@link TaskDao}
 */
public class Task {
    private String description;
    private User user;
    private TaskState state;

    public Task(String description, User user, TaskState state) {
        this.description = description;
        this.user = user;
        this.state = state;
    }

    public Task(){ }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    public TaskState getState() {
        return state;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
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
