package com.example.models;

import com.example.demo.enums.ETaskStatus;

public class Task {

    private String name;

    private ETaskStatus status;

    public Task(String name) {
        this.name = name;
        this.status = ETaskStatus.CREATED;
    }

    public String getName() {
        return name;
    }

    public ETaskStatus getStatus() {
        return status;
    }

    public void setStatus(ETaskStatus status) {
        this.status = status;
    }
}
