package com.example.demo.repository;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskRepositoryImpl implements TaskRepository {

    Map<String, Task> taskMap = new HashMap<>();

    @Override
    public void addTask(Task task) {
        taskMap.put(task.getName(), task);
    }

    @Override
    public void removeTask(Task task) {
        taskMap.remove(task.getName());
    }

    @Override
    public void updateTask(Task task) {
        taskMap.put(task.getName(), task);
    }

    @Override
    public Task getTaskByName(String taskName) {
       return taskMap.get(taskName);
    }

    @Override
    public Task getTaskByUser(User user) {
        // Переделать List и правильный поиск. ПОка не делать .
        // Если не будет использоваться то удалить
        return taskMap.get(user.getName());
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(taskMap.values());
    }
}
