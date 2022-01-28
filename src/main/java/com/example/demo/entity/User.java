package com.example.demo.entity;

import java.util.List;

public class User {

    private String name;
    private String right;
    private List<String> taskName;

    public User(String name, String right, List<String> taskName) {
        this.name = name;
        this.right = right;
        this.taskName = taskName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
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
                ", right='" + right + '\'' +
                ", taskName=" + taskName +
                '}';
    }
}
