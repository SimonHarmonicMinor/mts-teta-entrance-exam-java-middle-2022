package com.example.demo.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class TaskList {
    private static final Logger LOG = LoggerFactory.getLogger(TaskList.class);
    protected static HashMap<String, ArrayList<Task>> taskList = new HashMap<>();

    public ArrayList<Task> getTaskListForUser(String user) {
        if (!taskList.containsKey(user)) {
            return new ArrayList<>();
        }

        return taskList.get(user)
                .stream()
                .filter(task -> !task.isTaskDeleted())
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
    }
}
