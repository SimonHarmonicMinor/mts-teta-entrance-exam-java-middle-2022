package com.example.demo.model;

import com.example.demo.enums.Command;

public class Request {
    private final String user;
    private final Command command;
    private final String arg;

    public Request(String user, Command command, String arg) {
        this.user = user;
        this.command = command;
        this.arg = arg;
    }

    public String getUser() {
        return user;
    }

    public Command getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }
}
