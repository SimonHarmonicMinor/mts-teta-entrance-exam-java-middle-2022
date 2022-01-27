package com.example.demo;

public class Request {

    private String userName;
    private Command command;
    private String arg;

    public Request(String request) {
        String[] split = request.split(" ");
        this.userName = split[0];
        this.command = Command.findByKey(split[1]);
        this.arg = split[2];

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
