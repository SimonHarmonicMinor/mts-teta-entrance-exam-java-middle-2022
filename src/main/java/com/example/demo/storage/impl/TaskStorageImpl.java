package com.example.demo.storage.impl;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.storage.TaskStorage;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TaskStorageImpl implements TaskStorage {
    private final Set<Task> storage = new LinkedHashSet<>();

    @Override
    public void save(Task task) {
        storage.add(task);
    }

    @Override
    public void delete(Task task) {
        storage.remove(task);
    }

    @Override
    public boolean existsByName(String taskName) {
        return storage.stream()
                .anyMatch(task -> task.getName().equals(taskName));
    }

    @Override
    public Optional<Task> findByName(String taskName) {
        return storage.stream()
                .filter(task -> task.getName().equals(taskName))
                .findFirst();
    }

    @Override
    public List<Task> listAll(User sender, User owner) {
        return storage.stream()
                .filter(task -> task.getOwner().equals(owner))
                .filter(task -> task.isOpened() || task.getOwner().equals(sender))
                .collect(Collectors.toList());
    }
}
