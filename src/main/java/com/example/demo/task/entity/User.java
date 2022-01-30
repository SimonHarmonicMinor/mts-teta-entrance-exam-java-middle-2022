package com.example.demo.task.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by fkshistero on 30.01.2022.
 */
public class User {
    private String name;
    private HashMap<String, Task> tasks = new HashMap<>();

    public User (String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Task getTask(String nameTask) {
        return tasks.get(nameTask);
    }

    public List<String> getNameTasks() {
        return new ArrayList<>(tasks.keySet());
    }

    public void addTask(Task task) {
        this.tasks.put(task.getName(), task);
    }

    public boolean containsTask(String nameTask) {
        return this.tasks.containsKey(nameTask);
    }

    public void deleteTask(String taskName) {
        this.tasks.remove(taskName);
    }
}
