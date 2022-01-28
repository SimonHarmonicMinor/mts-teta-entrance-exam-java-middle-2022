package com.example.demo.repositories;

import com.example.demo.models.Task;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.models.TaskStatus.DELETED;

public class TaskRepositoryImpl implements TaskRepository {
    private static final Set<Task> tasks = new HashSet<>();

    @Override
    public Optional<Task> findByName(String name) {
        return tasks.stream()
                .filter(task -> name.equals(task.getName()))
                .findFirst();
    }

    @Override
    public List<Task> findAllNoDeletedByUserName(String userName) {
        return tasks.stream()
                .filter(task -> userName.equals(task.getUser().getName()) &&
                        DELETED != task.getStatus())
                .collect(Collectors.toList());
    }

    @Override
    public Task create(Task task) {
        tasks.add(task);
        return task;
    }

    @Override
    public Task updateStatus(Task task) {
        tasks.add(task);
        return task;
    }
}
