package com.example.demo.enums;

public enum CommandType {
    CREATE_TASK("CREATE_TASK"),
    CLOSE_TASK("CLOSE_TASK"),
    DELETE_TASK("DELETE_TASK"),
    REOPEN_TASK("REOPEN_TASK"),
    LIST_TASK("LIST_TASK"),
    DELETE_ALL("__DELETE_ALL");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return String.format("%s", command);
    }
}
