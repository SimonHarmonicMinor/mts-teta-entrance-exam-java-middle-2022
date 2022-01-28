package com.example.demo.repository;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;

import java.util.List;

public interface TaskRepository {

    void addTask(Task task);

    void removeTask(Task task);

    void updateTask(Task task);

    Task getTaskByName(String taskName);

    Task getTaskByUser(User user);

    List<Task> getAllTask();

}
