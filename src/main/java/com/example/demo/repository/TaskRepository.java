package com.example.demo.repository;

import com.example.demo.type.TaskStatus;

public interface TaskRepository {

    void createTask(String taskName);

    TaskStatus readStatus(String taskName);

    void updateTask(String taskName, TaskStatus taskStatus);

    void removeTask(String taskName);

}
