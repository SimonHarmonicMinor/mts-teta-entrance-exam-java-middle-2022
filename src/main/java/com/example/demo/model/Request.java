package com.example.demo.model;

import java.util.Optional;
import java.util.stream.Stream;

public class Request {
    public enum Command {
        CREATE_TASK, DELETE_TASK, CLOSE_TASK, REOPEN_TASK, LIST_TASK;

        public static Optional<Command> fromValue(String v) {
            return Stream.of(Command.values()).filter(c -> c.name().equalsIgnoreCase(v)).findFirst();
        }
    }

    private String user;
    private Command command;
    private String arg;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public Request(String user, Command command, String arg) {
        this.user = user;
        this.command = command;
        this.arg = arg;
    }
}
