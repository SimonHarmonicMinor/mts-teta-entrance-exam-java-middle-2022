package com.example.demo.enumerated;

public enum Result {

    CREATED("Task was created"),
    DELETED("Task was deleted"),
    CLOSED("Task was closed"),
    REOPENED("Task was reopened"),
    TASKS("All tasks"),
    WRONG_FORMAT("Wrong format"),
    ACCESS_DENIED("Access denied"),
    ERROR("Error");

    private final String description;

    Result(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
