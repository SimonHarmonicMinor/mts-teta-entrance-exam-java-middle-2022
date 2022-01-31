package com.example.demo.model;

import com.example.demo.model.enums.State;

public class Task {
    private String title;
    private State state = State.CREATED;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Task(String title) {
        this.title = title;
    }
}
