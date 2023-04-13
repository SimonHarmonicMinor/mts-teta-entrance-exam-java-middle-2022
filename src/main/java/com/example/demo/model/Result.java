package com.example.demo.model;

public enum Result {
    CREATED("CREATED"),
    DELETED("DELETED"),
    CLOSED("CLOSED"),
    REOPENED("REOPENED"),
    TASKS("TASKS"),
    WRONG_FORMAT("WRONG_FORMAT"),
    ACCESS_DENIED("ACCESS_DENIED"),
    ERROR("ERROR");
    public final String val;

    Result(String val) {
        this.val = val;
    }
}
