package com.example.demo.entity;

import java.util.List;
import java.util.Optional;

/**
 * Сущность User
 */

public class User {

    private String name;
    private List<String> taskName;

    public User(String name, List<String> taskName) {
        this.name = name;
        this.taskName = taskName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTaskName() {
        return taskName;
    }

    public void setTaskName(List<String> taskName) {
        this.taskName = taskName;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", taskName=" + taskName +
                '}';
    }
}
