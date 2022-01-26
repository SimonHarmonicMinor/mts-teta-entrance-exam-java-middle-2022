package com.example.demo.models;

public class Request {
    private String user;
    private String command;
    private String arg;

    public Request(String user, String command, String arg) {
        this.user = user;
        this.command = command;
        this.arg = arg;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
