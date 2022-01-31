package com.example.demo.entity;

public class Task {

    private String name;
    private String userName;
    private boolean active;

    public Task(String name, String userName, boolean active) {
        this.name = name;
        this.userName = userName;
        this.active = active;
    }

    public Task() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return name;
    }
}
