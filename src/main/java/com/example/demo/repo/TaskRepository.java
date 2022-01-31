package com.example.demo.repo;

import com.example.demo.model.Task;
import com.example.demo.model.User;

import java.util.List;

public interface TaskRepository {
    Task getByTaskName(String name);

    List<Task> getByUser(User user);

    void add(Task task);

    void delete(Task task);

    boolean isContainedByTaskName(String taskName);
}
