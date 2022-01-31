package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private String name;
    private final ArrayList<Task> tasks = new ArrayList<>(Collections.emptyList());

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public boolean hasTask(Task task) {
        return tasks.contains(task);
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public User(String name) {
        this.name = name;
    }

}
