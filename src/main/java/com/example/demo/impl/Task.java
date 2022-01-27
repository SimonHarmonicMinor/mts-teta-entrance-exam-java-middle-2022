package com.example.demo.impl;

import com.example.demo.enums.Result;
import com.example.demo.enums.TaskStatus;

import java.util.HashSet;

public class Task {
    private TaskStatus status;
    private final String name;
    protected static HashSet<String> activeTaskNames = new HashSet<>();

    public Task(String name) {
        this.status = TaskStatus.CREATED;
        this.name = name;
        activeTaskNames.add(name);
    }

    public boolean isDeleted() {
        return status.equals(TaskStatus.DELETED);
    }

    public void close() throws Exception {
        if (isDeleted()) {
            throw new Exception(String.valueOf(Result.ERROR));
        }

        this.status = TaskStatus.CLOSED;
    }

    public void delete() throws Exception {
        if (status == TaskStatus.CREATED) {
            throw new Exception(String.valueOf(Result.ERROR));
        }

        this.status = TaskStatus.DELETED;
        activeTaskNames.remove(name);
    }

    public void reopen() throws Exception {
        if (isDeleted()) {
            throw new Exception(String.valueOf(Result.ERROR));
        }

        this.status = TaskStatus.CREATED;
    }

    public String getName() {
        return name;
    }

    public static boolean isNameExists(String name) {
        return activeTaskNames.contains(name);
    }
}
