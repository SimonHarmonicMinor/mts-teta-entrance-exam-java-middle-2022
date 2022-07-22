package com.example.demo;

public class Task {
    String TaskName; //наименование задачи
    String UserName; // создатель задачи
    String Status; // статус задачи

    public Task(String iTaskName, String iUserName)
    {
        super();
        TaskName = iTaskName;
        UserName = iUserName;
        Status = "CREATED";
    }
}
