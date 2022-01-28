package com.example.demo.models;

import com.example.demo.helpers.TaskInfo;
import com.example.demo.helpers.TaskState;

public class Task {
    private final String Name;
    private TaskState State;
    private final String User;

    public Task(String name, String user) {
        Name = name;
        User = user;
        State = TaskState.CREATED;
    }

    public Task(String name, TaskInfo taskInfo) {
        Name = name;
        State = taskInfo.getState();
        User = taskInfo.getUser();
    }

    public boolean close() {
        if (State.close().isEmpty()) {
            return false;
        }
        State = State.close().get();
        return true;
    }

    public boolean reopen() {
        if (State.reopen().isEmpty()) {
            return false;
        }
        State = State.reopen().get();
        return true;
    }

    public boolean delete() {
        if (State.delete().isEmpty()) {
            return false;
        }
        State = State.delete().get();
        return true;
    }

    @Override
    public String toString() {
        return Name;
    }

    public String getName() {
        return Name;
    }

    public String getUser() {
        return User;
    }

    public TaskState getState() {
        return State;
    }
}
