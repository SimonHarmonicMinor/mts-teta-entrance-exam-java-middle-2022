package com.example.demo.repository;

import com.example.demo.entity.Task;

import java.util.*;

/**
 * Имплементация репозитория задач
 */

public class TaskRepositoryImpl implements TaskRepository {

    /**
     * HashMap для хранения задач, где ключ - название задачи, значение - задача
     */
    Map<String, Task> taskMap = new HashMap<>();

    /**
     * Создание задачи
     */
    @Override
    public void addTask(Task task) {
        taskMap.put(task.getName(), task);
    }

    /**
     * Удаление задачи
     */
    @Override
    public void removeTask(Task task) {
        taskMap.remove(task.getName());
    }

    /**
     * Обновление задачи
     */
    @Override
    public void updateTask(Task task) {
        taskMap.put(task.getName(), task);
    }

    /**
     * Получение задачи по ее имени
     */
    @Override
    public Optional<Task> getTaskByName(String taskName) {
        return Optional.ofNullable(taskMap.get(taskName));
    }

    /**
     * Получение всех задач
     */
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(taskMap.values());
    }
}
