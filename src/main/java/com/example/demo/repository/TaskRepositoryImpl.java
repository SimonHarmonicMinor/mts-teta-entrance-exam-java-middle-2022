package com.example.demo.repository;

import com.example.demo.entity.Status;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;

import java.util.*;
import java.util.stream.Collectors;

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
     * Получение задач по юзеру
     */
    @Override
    public List<Task> getTasksByUser(User user) {
        // Переделать List и правильный поиск. ПОка не делать .
        // Если не будет использоваться то удалить
//        return taskMap.get(user.getName());
        return null;

    }

    /**
     * Получение всех задач
     */
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(taskMap.values());
    }
}
