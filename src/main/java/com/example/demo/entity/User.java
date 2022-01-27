package com.example.demo.entity;

import java.util.List;

public class User {

    private String name;
    private String right;
    private List<String> taskName;

    public User(String name, String right, List<Task> taskList) {
        this.name = name;
        this.right = right;
        this.taskList = taskList;
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

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", right='" + right + '\'' +
                ", taskList=" + taskList +
                '}';
    }
}
