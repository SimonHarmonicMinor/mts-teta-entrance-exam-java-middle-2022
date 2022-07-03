package com.example.demo.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class User {

    private final String name;

    private final LinkedHashMap<String, Task> tasks = new LinkedHashMap<>();

    public User(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public boolean hasTask(String taskName) {
        return tasks.containsKey(taskName);
    }

    public void addTask(Task task) {
        tasks.put(task.getName(), task);
    }

    public boolean closeTask(String taskName) {
        Task task = tasks.get(taskName);
        return task.close(); //полагаемся на валидацию, task != null
    }

    public boolean reopenTask(String taskName) {
        Task task = tasks.get(taskName);
        return task.reopen(); //полагаемся на валидацию, task != null
    }

    public boolean deleteTask(String taskName) {
        Task task = tasks.get(taskName);
        if (TaskState.CLOSED != task.getState()) { //полагаемся на валидацию, task != null
            return false;
        }
        tasks.remove(taskName);
        return true;
    }
}
