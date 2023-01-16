package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class UserTaskList {
    private final List<Task> taskList = new ArrayList<>();

    public UserTaskList(Task task) {
        this.taskList.add(task);
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void putTask(Task task) {
        this.taskList.add(task);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Task task : taskList) {
            result.append(task.getTaskName());
            result.append(", ");
        }
        String str = result.toString();
        return str.substring(0, str.length()-2);
    }
}
