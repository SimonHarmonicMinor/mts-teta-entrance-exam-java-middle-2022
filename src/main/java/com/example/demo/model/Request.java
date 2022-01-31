package com.example.demo.model;

import java.util.Optional;
import java.util.stream.Stream;

public class Request {
    public enum Command {
        CREATE_TASK, DELETE_TASK, CLOSE_TASK, REOPEN_TASK, LIST_TASK;

        public static Optional<Command> fromValue(String v) {
            return Stream.of(Command.values()).filter(c -> c.name().equals(v)).findFirst();
        }
    }

    private boolean successful;
    private String user;
    private Command command;
    private String arg;

    public boolean isSuccessful() {
        return successful;
    }

    public String getUser() {
        return user;
    }

    public Command getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }

    public Request(boolean successful) {
        this.successful = false;
    }

    public Request(String user, Command command, String arg) {
        this.successful = true;
        this.user = user;
        this.command = command;
        this.arg = arg;
    }
}
