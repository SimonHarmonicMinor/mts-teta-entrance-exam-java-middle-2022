package com.example.demo.enumerated;

public enum Status {

    CREATED("Created task"),
    CLOSED("Closed task");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
