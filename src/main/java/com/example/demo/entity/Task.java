package com.example.demo.entity;

import com.example.demo.enums.TaskStatus;
import com.example.demo.validators.ChangeTaskStatusValidator;

import java.util.Objects;

import static com.example.demo.enums.TaskStatus.CREATED;
import static com.example.demo.enums.TaskStatus.DELETED;

public class Task {

    private final ChangeTaskStatusValidator validator = ChangeTaskStatusValidator.getInstance();

    private TaskStatus taskStatus;

    private final String taskName;

    public Task(String taskName) {
        this.taskName = taskName;
        this.taskStatus = CREATED;
    }

    public Task(String taskName, TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
        this.taskName = taskName;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public String getTaskName() {
        return taskName;
    }

    public void changeTaskStatus(TaskStatus newTaskStatus) {
        validator.validate(taskStatus, newTaskStatus);
        taskStatus = newTaskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        if (taskName.equals(task.taskName)
                && !DELETED.equals(taskStatus)
                && !DELETED.equals(task.taskStatus)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName);
    }
}
