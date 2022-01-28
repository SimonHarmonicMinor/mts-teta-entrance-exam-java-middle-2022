package com.example.demo.repositories;

import com.example.demo.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Optional<Task> findByName(String name);

    List<Task> findAllNoDeletedByUserName(String userName);

    Task create(Task task);

    Task updateStatus(Task task);
}
