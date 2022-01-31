package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;


public class User {

    private String nikName;
    private List<String> taskIdList = new ArrayList<>();

    public User(String nikName) {
        this.nikName = nikName;
    }

    public void addNewTask(String taskName) {
        taskIdList.add(taskName);
    }

    public String getNikName() {
        return nikName;
    }

    public void setNikName(String nikName) {
        this.nikName = nikName;
    }

    public List<String> getTaskIdList() {
        return taskIdList;
    }

    public void setTaskIdList(List<String> taskIdList) {
        this.taskIdList = taskIdList;
    }
}
