package com.example.demo.logic;

import com.example.demo.model.Result;
import com.example.demo.model.Task;
import com.example.demo.model.User;

import java.util.List;

public interface TaskService {
    Task findByName(String taskName);

    List<Task> findAllByUser(User user);

    Result createTask(String taskName, User user);

    Result openTask(String taskName, User user);

    Result closeTask(String taskName, User user);

    Result deleteTask(String taskName, User user);
}
