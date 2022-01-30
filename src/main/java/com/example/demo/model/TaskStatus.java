package com.example.demo.model;

public enum TaskStatus {
    CREATED,
    CLOSED,
    DELETED;

    public String value() {
        return name();
    }
    public static TaskStatus fromValue(String v) {
        return valueOf(v);
    }
}