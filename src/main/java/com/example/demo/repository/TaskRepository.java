package com.example.demo.repository;

import com.example.demo.exceptions.AccessDeniedException;
import com.example.demo.exceptions.TaskProcessingException;
import com.example.demo.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskRepository {

    public static final TaskRepository INSTANCE = new TaskRepository();

    private final Map<String, List<Task>> userTasks;
    private final Map<Task, Task> allTasks;

    private TaskRepository() {
        userTasks = new HashMap<>();
        allTasks = new HashMap<>();
    }

    public void create(Task task) throws TaskProcessingException {
        if (allTasks.containsKey(task)) {
            throw new TaskProcessingException();
        }

        allTasks.put(task, task);
        userTasks.computeIfAbsent(task.getOwner(), k -> new ArrayList<>()).add(task);
    }

    public void delete(Task task) throws AccessDeniedException, TaskProcessingException {
        Task result = getTask(task);
        if (result.isActive()) {
            throw new TaskProcessingException();
        }

        allTasks.remove(result);
        userTasks.get(result.getOwner()).remove(result);
    }

    public void close(Task task) throws AccessDeniedException, TaskProcessingException {
        changeStatus(task, false);
    }

    public void reopen(Task task) throws AccessDeniedException, TaskProcessingException {
        changeStatus(task, true);
    }

    public List<Task> getTasks(String user) {
        return userTasks.get(user).stream()
                .filter(Task::isActive)
                .collect(Collectors.toList());
    }

    private void changeStatus(Task task, boolean active) throws AccessDeniedException, TaskProcessingException {
        Task result = getTask(task);
        if (result.isActive() == active) {
            throw new TaskProcessingException();
        }

        result.setActive(active);
    }

    private Task getTask(Task task) throws TaskProcessingException, AccessDeniedException {
        Task result = allTasks.get(task);
        if (result == null) {
            throw new TaskProcessingException();
        }

        if (!result.getOwner().equals(task.getOwner())) {
            throw new AccessDeniedException();
        }

        return result;
    }
}
