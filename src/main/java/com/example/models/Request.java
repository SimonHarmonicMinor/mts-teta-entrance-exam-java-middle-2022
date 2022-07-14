package com.example.models;

import com.example.demo.enums.ECommand;

public class Request {

    private String userName;

    private ECommand command;

    private String arg;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String user) {
        this.userName = user;
    }

    public ECommand getCommand() {
        return command;
    }

    public void setCommand(ECommand command) {
        this.command = command;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
