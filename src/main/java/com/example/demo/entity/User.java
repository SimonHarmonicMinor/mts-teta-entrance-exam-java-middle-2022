package com.example.demo.entity;

import java.util.Set;

import static java.util.Collections.singleton;

public class User {

    private final String userName;

    private Set<Task> tasks;

    public User(String userName, Set<Task> tasks) {
        this.userName = userName;
        this.tasks = tasks;
    }

    public User(String userName, Task task) {
        this.userName = userName;
        this.tasks = singleton(task);
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public String getUserName() {
        return userName;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
