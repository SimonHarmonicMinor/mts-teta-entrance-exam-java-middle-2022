package com.example.demo.entity;

public class Request {

    private User user;
    private String additionalParam;
    private Command command;

    public Request(User user, String taskName, Command command) {
        this.user = user;
        this.additionalParam = taskName;
        this.command = command;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAdditionalParam() {
        return additionalParam;
    }

    public void setAdditionalParam(String additionalParam) {
        this.additionalParam = additionalParam;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "Request{" +
                "user=" + user +
                ", taskName='" + additionalParam + '\'' +
                ", command=" + command +
                '}';
    }
}
