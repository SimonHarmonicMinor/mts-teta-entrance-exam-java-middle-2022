package com.example.demo.logic;

import com.example.demo.model.Task;
import com.example.demo.model.User;

import java.util.List;

public interface TaskService {
    Task findByName(String taskName);

    List<Task> findAllByUser(User user);

    void createTask(String taskName, User user);

    void openTask(String taskName);

    void closeTask(String taskName);

    void deleteTask(String taskName);
}
