package com.example.demo.model;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.model.enums.Command;

public class Request {
    private User user;
    private Command command;
    private Task task;
    private String taskName;
    private User listedUser;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getListedUser() {
        return listedUser;
    }

    public void setListedUser(User listedUser) {
        this.listedUser = listedUser;
    }
}
