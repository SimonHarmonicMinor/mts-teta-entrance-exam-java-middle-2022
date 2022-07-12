package com.example.demo.enumerated;

public enum Command {

    CREATE_TASK("Create new task"),
    CLOSE_TASK("Close task"),
    DELETE_TASK("Delete task"),
    REOPEN_TASK("Reopen task"),
    LIST_TASK("Get task list"),
    __DELETE_ALL("Delete all tasks");

    private final String description;

    Command(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
