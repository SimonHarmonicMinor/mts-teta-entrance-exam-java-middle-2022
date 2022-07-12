package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.model.enums.TaskStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskRepository {
    private final static List<Task> taskListStorage = new ArrayList();

    public static void add(Task task) {
        taskListStorage.add(task);
    }

    public static void clear() {
        taskListStorage.clear();
    }

    public static List<Task> getByCreatedUserName(String userName) {
        return taskListStorage.stream()
                .filter(t -> t.getUserName().equals(userName)
                        && t.getTaskStatus() != TaskStatus.DELETED)
                .sorted(Comparator.comparing(Task::getCreateDate))
                .collect(Collectors.toList());
    }

    public static Task getByTaskName(String taskName) {
        return taskListStorage.stream()
                .filter(t -> t.getTaskName().equals(taskName)
                        && t.getTaskStatus() != TaskStatus.DELETED)
                .findFirst().orElse(null);
    }
}

