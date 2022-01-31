package com.example.demo.model.types;

public enum ResponseType {

    CREATED("CREATED"),
    DELETED("DELETED"),
    CLOSED("CLOSED"),
    REOPENED("REOPENED"),
    TASKS("TASKS"),
    WRONG_FORMAT("WRONG_FORMAT"),
    ACCESS_DENIED("ACCESS_DENIED"),
    ERROR("ERROR");

    private final String responseName;

    ResponseType(String responseName) {
        this.responseName = responseName;
    }

    public String getResponseName() {
        return responseName;
    }
}
