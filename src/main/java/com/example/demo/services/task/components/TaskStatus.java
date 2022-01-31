package com.example.demo.services.task.components;

import com.example.demo.services.task.TaskException;

import java.util.List;

import static com.example.demo.services.task.components.TaskStatus.LifeCycle.*;

public enum TaskStatus {

    CREATED("CREATED"),
    REOPENED("REOPENED"),
    CLOSED("CLOSED"),
    DELETED("DELETED");

    private final String title;

    TaskStatus(String title) {
        this.title = title;
    }

    public String getTaskStatusTitle() {
        return title;
    }

    public LifeCycle getLifeCycle() {
        switch (this.title) {
            case "DELETED":
                return DELETED_LIFECYCLE;
            case "CLOSED":
                return CLOSED_LIFECYCLE;
            case "CREATED":
            case "REOPENED":
                return CREATED_LIFECYCLE;
            default:
                throw new TaskException();
        }
    }

    public enum LifeCycle {
        CREATED_LIFECYCLE(List.of(CLOSED)),
        CLOSED_LIFECYCLE(List.of(CREATED, DELETED)),
        DELETED_LIFECYCLE(List.of());

        final List<TaskStatus> availableStatusList;

        LifeCycle(List<TaskStatus> availableStatusList) {
            this.availableStatusList = availableStatusList;
        }

        public List<TaskStatus> getAvailableStatusList() {
            return availableStatusList;
        }
    }
}
