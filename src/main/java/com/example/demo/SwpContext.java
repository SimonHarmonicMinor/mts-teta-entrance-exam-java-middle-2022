package com.example.demo;

import com.example.demo.model.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SwpContext {
    private final List<Task> tasks = new ArrayList<>();

    public Task findByName(String name) {
        return tasks.stream()
                .filter(task -> Objects.equals(task.getName(), name))
                .filter(task -> !task.getState().equals(Task.TaskState.DELETED))
                .findFirst().orElse(null);
    }

    public List<Task> findByUser(String userId) {
        return tasks.stream()
                .filter(task -> Objects.equals(task.getCreator(), userId))
                .filter(task -> !task.getState().equals(Task.TaskState.DELETED))
                .collect(Collectors.toList());
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
}