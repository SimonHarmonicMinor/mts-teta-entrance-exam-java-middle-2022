package com.example.entities;


import java.util.ArrayList;

public class User {

    private final String userName;
    private final ArrayList<Task> taskList;

    public User(String userName) {
        this.userName = userName;
        this.taskList = new ArrayList<>();
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public String getUserName() {
        return userName;
    }

    public boolean checkUserHaveCurrentTaskByTaskName(String taskName) {
        boolean temp = false;
        for (Task task : taskList) {
            if (task.getTaskName().equals(taskName)) {
                temp = true;
                break;
            }
        }
        return temp;
    }

}
