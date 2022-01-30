package com.example.demo.service;

import com.example.demo.model.ResultType;
import com.example.demo.model.Task;
import com.example.demo.model.TaskState;

import java.util.*;
import java.util.stream.Collectors;

public class TaskStorageService {
    private final Map<Task, String> activeTasksUserMap;
    private final Map<String, List<Task>> deletesUserTasksMap;

    public TaskStorageService() {
        activeTasksUserMap = new HashMap<>();
        deletesUserTasksMap = new HashMap<>();
    }

    public ResultType addTask(Task task, String user) {
        if (activeTasksUserMap.containsKey(task)) {
            return ResultType.ERROR;
        }
        activeTasksUserMap.put(task, user);
        return ResultType.CREATED;
    }

    public Optional<Task> getTask(String taskName) {
        return activeTasksUserMap.keySet()
                .stream()
                .filter(task -> task.getName().equals(taskName))
                .findFirst();
    }

    public ResultType deleteTask(Task task, String requestedUser) {
        if (!activeTasksUserMap.get(task).equals(requestedUser)) {
            return ResultType.ACCESS_DENIED;
        }
        if (TaskState.CLOSED.equals(task.getTaskState())) {
            String user = activeTasksUserMap.get(task);
            activeTasksUserMap.remove(task);
            task.setTaskState(TaskState.DELETED);
            deletesUserTasksMap.computeIfAbsent(user, k -> new LinkedList<>());
            deletesUserTasksMap.get(user).add(task);
            return ResultType.DELETED;
        } else {
            return ResultType.ERROR;
        }
    }

    public ResultType closeTask(Task task, String requestedUser) {
        if (!activeTasksUserMap.get(task).equals(requestedUser)) {
            return ResultType.ACCESS_DENIED;
        }
        if (TaskState.CREATED.equals(task.getTaskState())) {
            task.setTaskState(TaskState.CLOSED);
            return ResultType.CLOSED;
        } else {
            return ResultType.ERROR;
        }
    }

    public ResultType openTask(Task task, String requestedUser) {
        if (!activeTasksUserMap.get(task).equals(requestedUser)) {
            return ResultType.ACCESS_DENIED;
        }
        if (TaskState.CLOSED.equals(task.getTaskState())) {
            task.setTaskState(TaskState.CREATED);
            return ResultType.REOPENED;
        } else {
            return ResultType.ERROR;
        }
    }

    public List<Task> getListOfTasksForTheUser(String user) {
        return activeTasksUserMap.keySet()
                .stream()
                .filter(task -> activeTasksUserMap.get(task).equals(user))
                .collect(Collectors.toList());
    }
}
