package com.example.demo.TasksService;

public class Task {

    private final String Name;
    private TasksStatuses Status;
    private final String User;

    public Task(String name, TasksStatuses status, String user){
        Name = name;
        Status = status;
        User = user;
    }

    public String getName() {
        return Name;
    }

    public TasksStatuses getStatus() {
        return Status;
    }

    public void setStatus(TasksStatuses status){
        this.Status = status;
    }

    public String getUser() {
        return User;
    }

}
