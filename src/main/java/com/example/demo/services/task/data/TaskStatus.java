package com.example.demo.services.task.data;

import java.util.List;

import static com.example.demo.services.task.data.TaskStatus.LifeCycle.*;

public enum TaskStatus {

    CREATED("CREATED", CREATED_LIFECYCLE),
    REOPENED("REOPENED", CREATED_LIFECYCLE),
    CLOSED("CLOSED", CLOSED_LIFECYCLE),
    DELETED("DELETED", DELETED_LIFECYCLE);

    private String title;
    LifeCycle lifeCycle;

    TaskStatus(String title, LifeCycle lifeCycle) {
    }

    public String getTaskStatusTitle() {
        return title;
    }

    public LifeCycle getLifeCycle() {
        return lifeCycle;
    }

    public enum LifeCycle {
        CREATED_LIFECYCLE(List.of(CLOSED)),
        CLOSED_LIFECYCLE(List.of(CREATED, DELETED)),
        DELETED_LIFECYCLE(List.of());

        List<TaskStatus> availableStatusList;

        LifeCycle(List<TaskStatus> availableStatusList) {
        }

        public List<TaskStatus> getAvailableStatusList() {
            return availableStatusList;
        }
    }
}
