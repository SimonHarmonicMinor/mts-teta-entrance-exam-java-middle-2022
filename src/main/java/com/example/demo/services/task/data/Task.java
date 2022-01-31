package com.example.demo.services.task.data;

import com.example.demo.services.task.TaskException;

import java.util.List;

import static com.example.demo.services.task.TaskException.Type.WRONG_FORMAT;
import static com.example.demo.services.task.data.TaskStatus.CREATED;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class Task {
    private String title;
    private TaskStatus taskStatus;

    private Task() {
    }

    public Task(String title) throws TaskException {
        this.setTitle(title);
        this.taskStatus = CREATED;
    }

    public String getTaskTitle() {
        return title;
    }

    private void setTitle(String title) throws TaskException {
        checkTitle(title);

        this.title = title;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    private void checkTitle(String title) throws TaskException {
        if (isBlank(title))
            throw new TaskException(WRONG_FORMAT);
    }

    public boolean isAvailableLifeCycle() {
        return !this.taskStatus.getLifeCycle().getAvailableStatusList().isEmpty();
    }

    public boolean checkStatus(List<TaskStatus> taskStatusList) throws TaskException {
        return taskStatusList
                .stream()
                .map(status -> status.getTaskStatusTitle().equals(this.getTaskStatus().getTaskStatusTitle()))
                .anyMatch(any -> any.equals(true));
    }

    public String updateStatusBy(TaskStatus taskStatus) throws TaskException {
        if (!this.taskStatus.getLifeCycle().getAvailableStatusList().contains(taskStatus))
            throw new TaskException();

        this.taskStatus = taskStatus;

        return this.taskStatus.getTaskStatusTitle();
    }
}
