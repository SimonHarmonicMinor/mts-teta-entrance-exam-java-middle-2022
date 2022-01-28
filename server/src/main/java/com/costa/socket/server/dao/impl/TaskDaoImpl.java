package com.costa.socket.server.dao.impl;

import com.costa.socket.server.dao.TaskDao;
import com.costa.socket.server.model.Task;
import com.costa.socket.server.model.TaskState;
import com.costa.socket.server.model.User;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class TaskDaoImpl implements TaskDao {
    private final List<Task> tasks = new CopyOnWriteArrayList<>();

    @Override
    public Optional<Task> findByDescription(String description) {
        return tasks.stream()
                .filter(task -> task.getDescription().equals(description)
                        && !task.getState().equals(TaskState.DELETED))
                .findFirst();
    }

    @Override
    public List<Task> findAll() {
        return tasks.stream()
                .filter(task -> !task.getState().equals(TaskState.DELETED))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findAllByUser(User user) {
        return tasks.stream()
                .filter(task -> task.getUser().equals(user)
                        && !task.getState().equals(TaskState.DELETED))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findAllByUserAndTaskState(User user, TaskState taskState) {
        return tasks.stream()
                .filter(task -> task.getUser().equals(user)
                        && task.getState().equals(taskState))
                .collect(Collectors.toList());
    }

    @Override
    public boolean save(Task task) {
        tasks.stream()
                .filter(t -> !t.getState().equals(TaskState.DELETED))
                .collect(Collectors.toList())
                .remove(task);

        return tasks.add(task);
    }

    @Override
    public List<Task> findAllByUserName(String userName) {
        return tasks.stream()
                .filter(task -> task.getUser().getName().equals(userName)
                        && !task.getState().equals(TaskState.DELETED))
                .collect(Collectors.toList());
    }
}
