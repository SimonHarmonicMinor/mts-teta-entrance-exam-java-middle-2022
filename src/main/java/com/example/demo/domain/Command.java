package com.example.demo.domain;

import com.example.demo.helper.RequestAction;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(userName, command.userName) &&
                action == command.action &&
                Objects.equals(arg, command.arg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, action, arg);
    }
}
