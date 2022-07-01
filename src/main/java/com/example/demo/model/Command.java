package com.example.demo.model;

import java.util.Arrays;

public enum Command {
    CREATE_TASK("CREATE_TASK"),
    CLOSE_TASK("CLOSE_TASK"),
    DELETE_TASK("DELETE_TASK"),
    REOPEN_TASK("REOPEN_TASK"),
    LIST_TASK("LIST_TASK"),
    DELETE_ALL("__DELETE_ALL"),
    UNKNOWN("UNKNOWN");
    private final String value;

    Command(String value) {
        this.value = value;
    }

    public static Command valueOfCode(String code) {
        return Arrays.stream(Command.values())
                .filter(val -> val.value.equals(code))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
