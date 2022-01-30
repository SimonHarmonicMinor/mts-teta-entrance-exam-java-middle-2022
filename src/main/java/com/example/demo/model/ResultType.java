package com.example.demo.model;

public enum ResultType {
    CREATED("CREATED"),
    DELETED("DELETED"),
    CLOSED("CLOSED"),
    REOPENED("REOPENED"),
    TASKS("TASKS"),
    WRONG_FORMAT("WRONG_FORMAT"),
    ACCESS_DENIED("ACCESS_DENIED"),
    ERROR("ERROR");

    private final String value;

    ResultType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
