package com.example.demo.model.types;

public enum CommandType {

    CREATE_TASK("CREATE_TASK"),
    DELETE_TASK("DELETE_TASK"),
    CLOSE_TASK("CLOSE_TASK"),
    REOPEN_TASK("REOPEN_TASK"),
    LIST_TASK("LIST_TASK"),
    UNKNOWN("UNKNOWN");

    private final String commandName;

    CommandType(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }

    public static CommandType getCommandType(String command) {
        for (CommandType type : values()) {
            if (type.getCommandName().equals(command)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
