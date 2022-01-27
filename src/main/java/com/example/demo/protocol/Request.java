package com.example.demo.protocol;

import com.example.demo.entity.User;

public class Request {
    private final User user;
    private final String command;
    private final String taskTitle;

    public Request(User user, String command, String task) {
        this.user = user;
        this.command = command;
        this.taskTitle = task;
    }

    public User getUser() {
        return user;
    }

    public String getCommand() {
        return command;
    }

    public String getArg() {
        return taskTitle;
    }
}
