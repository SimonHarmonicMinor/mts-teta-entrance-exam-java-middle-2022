package com.example.demo.model;

import java.util.Objects;

public class BaseCommandResult {
    private final Status status;

    public BaseCommandResult(Status status) {
        this.status = Objects.requireNonNull(status, "Command result status is null");
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return status.name();
    }

    public enum Status {
        CREATED,
        DELETED,
        CLOSED,
        REOPENED,
        TASKS,
        WRONG_FORMAT,
        ACCESS_DENIED,
        ERROR
    }
}
