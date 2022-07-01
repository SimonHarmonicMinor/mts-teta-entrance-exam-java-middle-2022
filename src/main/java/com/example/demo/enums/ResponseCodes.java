package com.example.demo.enums;

public enum ResponseCodes {
    CREATED("CREATED"),
    DELETED("DELETED"),
    CLOSED("CLOSED"),
    REOPENED("REOPENED"),
    TASKS("TASKS"),
    WRONG_FORMAT("WRONG_FORMAT"),
    ACCESS_DENIED("ACCESS_DENIED"),
    ERROR("ERROR");

    private final String response;

    ResponseCodes(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return String.format("response: %s", response);
    }
}
