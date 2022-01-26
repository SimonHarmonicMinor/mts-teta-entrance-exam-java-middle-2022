package com.example.demo;

public class Task {
// обьект задача
   // имя и статус перечисление
    public String taskname;
    public taskstatus status;
    public enum taskstatus {
        CREATED("CREATED"),
        CLOSED("CLOSED"),
        DELETED("DELETED");
        taskstatus(String status) {
        }
    }
    //Конструктор по имени
    public Task GetWithName(String name) {
        this.taskname = name;
        this.status = taskstatus.CREATED;
        return this;
    }

}
