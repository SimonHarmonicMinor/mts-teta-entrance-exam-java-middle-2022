package com.example.demo.task.entity;

import com.example.demo.task.status.StatusTask;

/**
 * Create by fkshistero on 30.01.2022.
 */

public class Task {
    private StatusTask status;
    private String name;

    public Task(String name) {
        this.setName(name);
        this.setStatus(StatusTask.CREATED);
    }

    public StatusTask getStatus() {
        return status;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }
}
