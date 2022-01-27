package com.example.demo.entity;

public class Task {

    private final User user;
    private final String title;
    private State state = State.CREATED;

    public Task(User user, String title) {
        this.user = user;
        this.title = title;
    }

    public enum State {
        CREATED, CLOSED, DELETED
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return title;
    }
}
