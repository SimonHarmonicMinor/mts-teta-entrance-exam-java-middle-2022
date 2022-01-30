package com.example.demo.repositories;

import com.example.demo.models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.models.TaskStatus.DELETED;

public class TaskRepositoryImpl implements TaskRepository {
    private static final List<Task> tasks = new ArrayList<>();

    @Override
    public Optional<Task> findByName(String name) {
        return tasks.stream()
                .filter(task -> task.getStatus() != DELETED)
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
