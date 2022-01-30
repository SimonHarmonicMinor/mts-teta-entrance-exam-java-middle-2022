package com.example.demo.model;

public enum CommandResult {
    CREATED,
    DELETED,
    CLOSED,
    REOPENED,
    TASKS,
    WRONG_FORMAT,
    ACCESS_DENIED,
    ERROR;

    public String value() {
        return name();
    }
    public static CommandResult fromValue(String v) {
        return valueOf(v);
    }
}