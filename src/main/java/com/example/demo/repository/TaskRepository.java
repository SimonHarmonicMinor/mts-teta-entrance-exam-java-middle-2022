package com.example.demo.repository;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для задач
 */

public interface TaskRepository {

    void addTask(Task task);

    void removeTask(Task task);

    void updateTask(Task task);

    Optional<Task> getTaskByName(String taskName);

    List<Task> getAllTasks();

}
