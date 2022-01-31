package com.example.demo;

public class Task {
    public Task() {
    }

    private String name;
    private String taskStatus;
    private String creator;

    public String getName() {return name;}
    public void setTitle(String name) {this.name = name;}
    public String getTaskStatus() {return taskStatus;}
    public void setTaskStatus(String taskStatus) {this.taskStatus = taskStatus;}
    public String getCreator() {return creator;}
    public void setCreator(String creator) {this.creator = creator;}
}