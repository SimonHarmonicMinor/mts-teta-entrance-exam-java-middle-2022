package com.example.demo;

import java.util.HashMap;
import java.util.Map;

public class TaskList {
    public static Map<String, UserTaskList> taskList = new HashMap<>(); //userName, taskList
    public static Task findTask(String neededTaskName) {
        for (UserTaskList userTaskList: taskList.values()) {
            for (Task task : userTaskList.getTaskList()) {
                if (task.getTaskName().equals(neededTaskName)) return task;
            }
        }
        return null;
    }
}
