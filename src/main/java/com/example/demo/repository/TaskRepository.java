package com.example.demo.repository;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;

public interface TaskRepository {

    void addTask(Task task);

    void removeTask(Task task);

    void updateTask(Task task);

    void getTaskByName(Task task);

    void getTaskByUser(User user);

}
