package com.example.demo.model;

public enum CommandType {
    CREATE_TASK("CREATE_TASK"),
    DELETE_TASK("DELETE_TASK"),
    CLOSE_TASK("CLOSE_TASK"),
    REOPEN_TASK("REOPEN_TASK"),
    LIST_TASK("LIST_TASK");

    private final String value;

    CommandType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
