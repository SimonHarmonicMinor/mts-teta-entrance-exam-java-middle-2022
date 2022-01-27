package com.example.demo.impl;

import com.example.demo.enums.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

public class TaskList {
    private static final Logger LOG = LoggerFactory.getLogger(TaskList.class);
    protected static HashMap<String, ArrayList<Task>> taskList = new HashMap<>();
    protected static HashMap<String, String> activeTasks = new HashMap<>();

    public ArrayList<Task> getTaskListForUser(String user) {
        if (!taskList.containsKey(user)) {
            return new ArrayList<>();
        }

        return taskList.get(user)
                .stream()
                .filter(task -> !task.isDeleted())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void addTask(String user, String taskName) throws Exception {
        var task = new Task(taskName);

        if (taskList.containsKey(user)) {
            var userTaskList = taskList.get(user);
            userTaskList.add(task);
            taskList.put(user, userTaskList);
            return;
        }

        var newArrayList = new ArrayList<Task>();
        newArrayList.add(task);

        taskList.put(user, newArrayList);
        activeTasks.put(task.getName(), user);
    }

    public void deleteTask(String user, String taskName) throws Exception {
        if (!isTaskBelongToUser(user, taskName)) {
            throw new Exception(String.valueOf(Result.ACCESS_DENIED));
        }

        if (taskList.containsKey(user)) {
            var currentTask = getTaskByNameAndUser(user, taskName);

            currentTask.get(0).delete();
            activeTasks.remove(taskName);
            return;
        }

        throw new Exception(String.valueOf(Result.ERROR));
    }

    public void closeTask(String user, String taskName) throws Exception {
        if (!isTaskBelongToUser(user, taskName)) {
            throw new Exception(String.valueOf(Result.ACCESS_DENIED));
        }

        if (taskList.containsKey(user)) {
            var currentTask = getTaskByNameAndUser(user, taskName);

            currentTask.get(0).close();
            return;
        }

        throw new Exception(String.valueOf(Result.ERROR));
    }

    public void reopenTask(String user, String taskName) throws Exception {
        if (!isTaskBelongToUser(user, taskName)) {
            throw new Exception(String.valueOf(Result.ACCESS_DENIED));
        }

        if (taskList.containsKey(user)) {
            var currentTask = getTaskByNameAndUser(user, taskName);

            currentTask.get(0).reopen();
            return;
        }

        throw new Exception(String.valueOf(Result.ERROR));
    }

    private ArrayList<Task> getTaskByNameAndUser(String user, String taskName) {
        return taskList.get(user)
                .stream()
                .filter(task -> !task.isDeleted() && Objects.equals(task.getName(), taskName))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean isTaskBelongToUser(String user, String taskName) {
        return !activeTasks.containsKey(taskName) && Objects.equals(activeTasks.get(taskName), user);
    }
}
