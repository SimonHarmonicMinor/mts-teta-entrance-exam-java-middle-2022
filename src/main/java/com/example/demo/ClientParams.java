package com.example.demo;

import java.util.HashMap;

public class ClientParams {

    HashMap<String, String> taskInfo = new HashMap<>();

    HashMap<String, String> deletedTasks = new HashMap<>();

    public HashMap<String, String> getTaskInfo() {
        return taskInfo;
    }

    public void createTaskInfo(String taskName, String taskStatus) {
        this.taskInfo.put(taskName, taskStatus);
    }

    public void updateTaskInfo(String taskName, String taskStatus) {
        if (taskStatus.equals("DELETED")) {
            this.taskInfo.remove(taskName);
            this.deletedTasks.put(taskName, taskStatus);
        } else {
            this.taskInfo.replace(taskName, taskStatus);
        }
    }
}

