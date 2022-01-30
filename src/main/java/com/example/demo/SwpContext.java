package com.example.demo;

import com.example.demo.model.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class SwpContext {
    private Map<String, Task> tasks = new HashMap<>();

    public Task findByName(String name) {
        return tasks.getOrDefault(name, null);
    }

    public List<Task> findByUser(String userId) {
        return tasks.values().stream().filter(task -> Objects.equals(task.getCreator(), userId)).collect(Collectors.toList());
    }

    public void addTask(Task task) {
        tasks.putIfAbsent(task.getName(), task);
    }

    public void deleteTask(String taskName) {
        tasks.remove(taskName);
    }
}