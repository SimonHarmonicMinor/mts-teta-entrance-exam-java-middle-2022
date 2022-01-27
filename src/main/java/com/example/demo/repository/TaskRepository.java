package com.example.demo.repository;

import com.example.demo.enums.TaskStatus;
import com.example.demo.models.Task;

import java.util.*;
import java.util.stream.Collectors;


public class TaskRepository {

    private Map<Long, Task> map = new HashMap<>();

    private Set<Task> findAll() {
        return new HashSet<>(map.values());
    }

    public Set<String> findAllNotDeleted(String user) {
        return findAll().stream().filter( t -> t.getUser().equals(user) && !t.getStatus().equals(TaskStatus.DELETED))
                .map(Task::getName)
                .collect(Collectors.toSet());
    }

    public boolean existByName(String name) {
        return findAll().stream().anyMatch(t -> t.getName().equals(name) && !t.getStatus().equals(TaskStatus.DELETED));
    }

    public Optional<Task> findByNameAndTaskStatus(String name, TaskStatus taskStatus) {
        return findAll().stream().filter(t -> t.getName().equals(name) && t.getStatus().equals(taskStatus)).findFirst();
    }

    public void save(Task task) {
        if (task != null) {
            if (task.getId() == null) {
                task.setId(getNextId());
            }
            map.put(task.getId(), task);
        } else {
            throw new RuntimeException("Task cannot be null");
        }
    }

    public void delete(Task task) {
        updateStatus(task, TaskStatus.DELETED);
    }

    public void close(Task task) {
        updateStatus(task, TaskStatus.CLOSED);
    }

    public void reopen(Task task) {
        updateStatus(task, TaskStatus.CREATED);
    }

    private void updateStatus(Task task, TaskStatus status) {
        task.setStatus(status);
        this.map.put(task.getId(), task);
    }

    private Long getNextId() {
        Long nextId;
        try {
            nextId = Collections.max(map.keySet()) + 1;
        } catch (NoSuchElementException e) {
            nextId = 1L;
        }
        return nextId;
    }

}
