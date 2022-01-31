package com.modules;

public class Task {
    private String author;
    private String status;
    private String name;

    public Task(Task task) {
        this.author = task.getAuthor();
        this.status = task.getStatus();
        this.name = task.name;
    }

    public Task(String author, String status, String name) {
        this.author = author;
        this.status = status;
        this.name = name;
    }

    public Task() {

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
