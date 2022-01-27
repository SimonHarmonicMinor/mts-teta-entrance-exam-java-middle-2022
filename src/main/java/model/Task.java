package model;

public class Task {
    public Task(String name, String taskStatus, String creator) {
        this.name = name;
        this.taskStatus = taskStatus;
        this.creator = creator;
    }

    public Task() {
    }

    private String name;
    private String taskStatus;
    private String creator;


    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getTaskStatus() {return taskStatus;}
    public void setTaskStatus(String taskStatus) {this.taskStatus = taskStatus;}
    public String getCreator() {return creator;}
    public void setCreator(String creator) {this.creator = creator;}
}
