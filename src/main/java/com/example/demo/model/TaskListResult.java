package com.example.demo.model;

import java.util.List;
import java.util.StringJoiner;

public class TaskListResult extends BaseCommandResult {
    private final List<Task> tasks;

    public TaskListResult(List<Task> tasks) {
        super(Status.TASKS);
        this.tasks = List.copyOf(tasks);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public String toString() {
        final StringJoiner tasksJoiner = new StringJoiner(", ", "[", "]");
        tasks.forEach(task -> tasksJoiner.add(task.toString()));
        return new StringJoiner(" ")
                .add(super.getStatus().toString())
                .add(tasksJoiner.toString())
                .toString();
    }
}
