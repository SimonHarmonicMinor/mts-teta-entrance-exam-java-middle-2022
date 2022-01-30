package com.example.demo.model;

public enum Command {
    CREATE_TASK,
    DELETE_TASK,
    CLOSE_TASK,
    REOPEN_TASK,
    LIST_TASK;

    public String value() {
        return name();
    }
    public static Command fromValue(String v) {
        return valueOf(v);
    }
}