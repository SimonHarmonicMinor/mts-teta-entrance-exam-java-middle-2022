package com.example.demo.entity;

/**
 * Сущность Request
 */

public class Request {

    private String userName;
    private Command command;
    private String additionalParam;

    public Request(String userName, Command command, String additionalParam) {
        this.userName = userName;
        this.command = command;
        this.additionalParam = additionalParam;
    }

    public Request() {

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

    public String getAdditionalParam() {
        return additionalParam;
    }

    public void setAdditionalParam(String additionalParam) {
        this.additionalParam = additionalParam;
    }

    @Override
    public String toString() {
        return "Request{" +
                "userName='" + userName + '\'' +
                ", command=" + command +
                ", additionalParam='" + additionalParam + '\'' +
                '}';
    }
}
