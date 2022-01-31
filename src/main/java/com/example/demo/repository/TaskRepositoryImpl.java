package com.example.demo.repository;

import com.example.demo.repository.entity.Task;
import com.example.demo.repository.entity.TaskStatus;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskRepositoryImpl implements TaskRepository {
    private Map<String, Task> tasksMap = new HashMap<>();

    @Override
    public void createTask(Task task) throws Exception {
        Collection<Task> tasks = tasksMap.values();
        Optional<Task> foundTask = tasks.stream()
                .filter(t -> task.getId().equals(t.getId())
                        || task.getName().equals(t.getName())
                        && !TaskStatus.DELETED.equals(t.getStatus()))
                .findFirst();
        if (foundTask.isPresent()) {
            throw new Exception();
        }
        tasksMap.put(task.getId(), task);
    }

    @Override
    public Optional<Task> findTaskByName(String name) {
        for (Map.Entry<String, Task> pair : tasksMap.entrySet()) {
            if (pair.getValue().getName().equals(name)) {
                return Optional.of(pair.getValue());
            }
        }
        return Optional.empty();
    }

    @Override
    public void updateTask(Task task) throws Exception {
        Optional<Task> taskByName = findTaskByName(task.getName());
        if (taskByName.isEmpty()) {
            throw new Exception();
        }
        tasksMap.put(task.getId(), task);
    }

    @Override
    public Collection<Task> findTasksByUser(String userName) {
        return tasksMap.values().stream()
                .filter(task -> userName.equals(task.getUser().getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void removeAll() {
        tasksMap.clear();
    }
}