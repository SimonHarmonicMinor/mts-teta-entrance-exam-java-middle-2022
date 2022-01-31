package com.example.demo.model;

public class Request {

    private String userName;
    private Command command;
    private String argument;

    public Request() {
    }

    public Request(String userName, Command command, String argument) {
        this.userName = userName;
        this.command = command;
        this.argument = argument;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

}
