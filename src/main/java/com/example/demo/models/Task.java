package com.example.demo.models;

import com.example.demo.models.statuses.CreatedStatus;
import com.example.demo.models.statuses.Status;

public class Task {
    private String user;
    private String title;
    private Status status;

    public Task(String user, String title) {
        this.status = new CreatedStatus(this);
        this.user = user;
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public String getTitle() {
        return this.title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
