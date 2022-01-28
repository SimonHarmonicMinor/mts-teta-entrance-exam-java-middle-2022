package com.example.demo.type;

public enum TaskStatus {

    CREATED("CREATED"),
    CLOSED("CLOSED"),
    DELETED("DELETED");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
