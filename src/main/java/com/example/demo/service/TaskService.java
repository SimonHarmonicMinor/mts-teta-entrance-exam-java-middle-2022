package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.model.User;

import java.util.List;

public interface TaskService {
    void createTask(User user, String taskName);
    void closeTask(User user, String taskName);
    void reopenTask(User user, String taskName);
    void delete(User user, String taskName);
    List<Task> listTasks(User sender, User owner);
}
