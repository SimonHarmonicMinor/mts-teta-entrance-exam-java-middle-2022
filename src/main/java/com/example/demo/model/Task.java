package com.example.demo.model;

import com.example.demo.enumerated.Status;

import java.util.Date;

public class Task {

    private String name;
    private String user;
    private Status status;
    private Date created = new Date();

    public Task(String name, String user) {
        this.name = name;
        this.user = user;
        this.status = Status.CREATED;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return user + ": " + name;
    }

}
