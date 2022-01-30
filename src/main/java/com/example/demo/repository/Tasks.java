package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.model.TaskStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tasks {
    private final static List<Task> taskList = new ArrayList<>();

    public static void add(Task task) {
        taskList.add(task);
    }

    public static List<Task> getByOwner(String userName) {
        return taskList.stream()
                .filter(t -> t.getOwner().equals(userName) && t.getStatus() != TaskStatus.DELETED).collect(Collectors.toList());
    }

    public static Task getByName(String taskName) {
        return taskList.stream()
                .filter(t -> t.getName().equals(taskName) && t.getStatus() != TaskStatus.DELETED).findFirst().orElse(null);
    }
}
