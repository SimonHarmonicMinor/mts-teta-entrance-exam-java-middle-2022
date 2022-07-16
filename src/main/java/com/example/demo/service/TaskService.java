package com.example.demo.service;

import com.example.demo.data.TaskTrackerData;
import com.example.demo.format.Answer;
import com.example.demo.model.Task;
import com.example.demo.model.User;

import java.util.List;

public class TaskService {

    private final TaskTrackerData data;

    public TaskService(TaskTrackerData data) {
        this.data = data;
    }

    public Answer create(String userName, String taskName) {
        if (data.isTaskActive(taskName)) {
            return Answer.ERROR;
        }
        User user = data.getUser(userName);
        try {
            data.userAddTask(user, taskName);
        } catch (Exception e) {
            return Answer.ERROR;
        }
        return Answer.CREATED;
    }

    public Answer close(String userName, String taskName) {
        if (!accessValidation(userName, taskName)) {
            return Answer.ACCESS_DENIED;
        }
        User user = data.getUser(userName);
        if (data.closeTask(user, taskName)) {
            return Answer.CLOSED;
        }
        return Answer.ERROR;
    }

    public Answer reopen(String userName, String taskName) {
        if (!accessValidation(userName, taskName)) {
            return Answer.ACCESS_DENIED;
        }
        User user = data.getUser(userName);
        if (data.reopenTask(user, taskName)) {
            return Answer.REOPENED;
        }
        return Answer.ERROR;
    }

    public Answer delete(String userName, String taskName) {
        if (!accessValidation(userName, taskName)) {
            return Answer.ACCESS_DENIED;
        }
        if (data.deleteTask(userName, taskName)) {
            return Answer.DELETED;
        }
        return Answer.ERROR;
    }

    private boolean accessValidation(String userName, String taskName) {
        return data.checkAccess(userName, taskName);
    }

    public void deleteAll() {
        data.deleteAll();
    }

    public String listTask(String userName) {
        List<Task> userTasks = data.getUserTasks(userName);
        if (userTasks.isEmpty()) {
            return "[]";
        }
        String taskListString = userTasks.stream().map(Task::getName).reduce("[", (sum, taskName) -> sum + taskName + ", ");
        int lastIndex = taskListString.lastIndexOf(",");
        return taskListString.substring(0, lastIndex) + "]";
    }
}
