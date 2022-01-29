package com.example.demo.TasksService;

public class Task {

    private final String Name;
    private TasksStatuses Status;
    private final String User;

    public Task(String Taskname, TasksStatuses Taskstatus, String Taskuser){
        Name = Taskname;
        Status = Taskstatus;
        User = Taskuser;
    }

    public String getName() {
        return Name;
    }

    public TasksStatuses getStatus() {
        return Status;
    }

    public void setStatus(TasksStatuses Taskstatus){
        this.Status = Taskstatus;
    }

    public String getUser() {
        return User;
    }

}
