package com.example.demo.model;

public enum TaskState {
    CREATED("CREATED"),
    CLOSED("CLOSED"),
    DELETED("DELETED");

    private final String value;

    TaskState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
