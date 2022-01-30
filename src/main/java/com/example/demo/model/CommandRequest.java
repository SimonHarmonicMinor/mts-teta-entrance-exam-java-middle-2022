package com.example.demo.model;

public class CommandRequest {
    private final String userName;
    private final String commandName;
    private final String commandArg;

    public CommandRequest(String userName, String commandName, String commandArg) {
        this.userName = userName;
        this.commandName = commandName;
        this.commandArg = commandArg;
    }

    public String getUserName() {
        return userName;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getCommandArg() {
        return commandArg;
    }
}
