package com.example.demo.entity;

import java.util.Objects;
import java.util.UUID;

public class Task {
    public enum State {
        CREATED, CLOSED, DELETED
    }

    private final UUID uuid; // чтобы иметь возможность хранить удаленные таски и открытый таск с одним названием
    private String name;
    private State state;
    private User user;

    public Task(String name, State state, User user) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.state = state;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setFields(Task other) {
        this.state = other.getState();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return uuid.equals(task.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", user=" + user.getName() +
                '}';
    }
}
