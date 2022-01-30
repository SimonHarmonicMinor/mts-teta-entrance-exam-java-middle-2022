package com.example.demo.models.statuses;

import com.example.demo.models.Task;

public abstract class Status {
    private final Task task;

    public Status(Task task) {
        this.task = task;
    }

    public abstract Boolean next();

    public abstract Boolean previous();

    public abstract int getId();

    public Task getTask() {
        return task;
    }
}
