package com.example.demo.core.data;

import com.example.demo.core.enums.Status;

public class Task {
    private String name;
    private Status status;
    private String owner;

    public Task(String name, Status status, String owner) {
        this.name = name;
        this.status = status;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(name);
        return sb.toString();
    }
}
