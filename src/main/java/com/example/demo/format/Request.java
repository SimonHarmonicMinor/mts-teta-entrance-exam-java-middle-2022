package com.example.demo.format;

public class Request {

    private final String userName;

    private final String arg;

    private final Command command;

    public Request(Command command, String userName, String arg) {
        this.userName = userName;
        this.arg = arg;
        this.command = command;
    }

    public String getUserName() {
        return userName;
    }

    public String getArg() {
        return arg;
    }

    public Command getCommand() {
        return command;
    }
}
