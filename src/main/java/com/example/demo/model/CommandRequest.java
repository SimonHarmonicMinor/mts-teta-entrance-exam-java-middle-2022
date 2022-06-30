package com.example.demo.model;

import com.example.demo.model.enums.Command;

public class CommandRequest {
    private final String userName;
    private final Command command;
    private final String arg;

    public CommandRequest(String userName, Command command, String arg) {
        this.userName = userName;
        this.command = command;
        this.arg = arg;
    }

    public String getUserName() {
        return userName;
    }

    public Command getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }
}
