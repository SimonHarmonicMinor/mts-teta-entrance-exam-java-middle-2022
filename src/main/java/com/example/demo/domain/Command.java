package com.example.demo.domain;

import com.example.demo.helper.RequestAction;

public class Command {
    private String userName;
    private RequestAction action;
    private String arg;

    public Command(String name, RequestAction action, String arg) {
        this.userName = name;
        this.action = action;
        this.arg = arg;
    }

    public Command() {
    }

    public String getUserName() {
        return userName;
    }

    public RequestAction getAction() {
        return action;
    }

    public String getArg() {
        return arg;
    }
}
