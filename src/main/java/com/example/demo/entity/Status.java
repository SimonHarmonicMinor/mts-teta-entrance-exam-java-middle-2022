package com.example.demo.entity;

public enum Status {
    CREATED("CREATED"),
    DELETED("DELETED"),
    CLOSED("CLOSED"),
    REOPENED("REOPENED"),
    TASKS("TASKS"),
    WRONG_FORMAT("WRONG_FORMAT"),
    ACCESS_DENIED("ACCESS_DENIED"),
    ERROR("ERROR");

    private String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }
}
