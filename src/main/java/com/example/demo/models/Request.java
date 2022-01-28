package com.example.demo.models;

public class Request {

    private final String arg;
    private final String command;
    private final String user;

    public Request(String[] parts) {
        this.arg = parts[2];
        this.command = parts[1];
        this.user = parts[0];
    }

    public String getCommand() {
        return command;
    }

    public String getUser() {
        return user;
    }

    public String getArg() {
        return arg;
    }
}
