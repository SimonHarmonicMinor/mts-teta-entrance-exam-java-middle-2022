package com.example.demo.model;

public class Request {

    private User user;
    private String command;
    private String requestArgument;

    public Request() {
    }

    public User getUser() {
        return user;
    }

    public String getCommand() {
        return command;
    }

    public String getRequestArgument() {
        return requestArgument;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setRequestArgument(String requestArgument) {
        this.requestArgument = requestArgument;
    }

    @Override
    public String toString() {
        return user + " " + command + " " + requestArgument;
    }
}
