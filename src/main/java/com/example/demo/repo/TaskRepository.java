package com.example.demo.repo;

import com.example.demo.model.Task;
import com.example.demo.model.User;

import java.util.List;

public interface TaskRepository {
    List<Task> findAll();

    Task findByName(String name);

    List<Task> findByUser(User user);

    boolean save(Task task);

    boolean delete(Task task);
}
