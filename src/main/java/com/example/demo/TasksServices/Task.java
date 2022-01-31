package com.example.demo.TasksServices;

public class Task {

    private final String Name;
    private TaskStatuses Status;
    private final String User;

    public Task(String name, TaskStatuses status, String user){
        Name = name;
        Status = status;
        User = user;
    }

    public String getName() {
        return Name;
    }

    public TaskStatuses getStatus() {
        return Status;
    }

    public void setStatus(TaskStatuses status){
        this.Status = status;
    }

    public String getUser() {
        return User;
    }
}
