package com.example.demo.repo;

import com.example.demo.model.Task;
import com.example.demo.model.User;

import java.util.List;

public interface TaskRepository {
    List<Task> findAll();

    Task findByTaskName(String name);

    List<Task> findByUser(User user);

    boolean create(Task task);

    boolean update(Task task);

    boolean delete(Task task);

    boolean isContainedByTaskName(String taskName);
}
