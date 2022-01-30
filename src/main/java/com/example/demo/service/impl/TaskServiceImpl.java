package com.example.demo.service.impl;

import com.example.demo.exception.ServiceException;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.service.TaskService;
import com.example.demo.storage.TaskStorage;
import com.example.demo.storage.impl.TaskStorageImpl;

import java.util.List;

public class TaskServiceImpl implements TaskService {
    private final TaskStorage taskStorage = new TaskStorageImpl();

    @Override
    public void createTask(User user, String taskName) {
        checkTaskExistence(taskName);
        taskStorage.save(new Task(taskName, user));
    }

    @Override
    public void closeTask(User user, String taskName) {
        final Task task = getAndCheckAccess(user, taskName);
        task.close();
    }

    @Override
    public void reopenTask(User user, String taskName) {
        final Task task = getAndCheckAccess(user, taskName);
        task.open();
    }

    @Override
    public void delete(User user, String taskName) {
        final Task task = getAndCheckAccess(user, taskName);
        taskStorage.delete(task);
    }

    @Override
    public List<Task> listTasks(User sender, User owner) {
        return taskStorage.listAll(sender, owner);
    }

    private void checkTaskExistence(String taskName) {
        if (taskStorage.existsByName(taskName)) {
            throw new ServiceException(ServiceException.ErrorType.COMMON);
        }
    }

    private Task getByName(String taskName) {
        return taskStorage.findByName(taskName)
                .orElseThrow(() -> new ServiceException(ServiceException.ErrorType.COMMON));
    }

    private Task getAndCheckAccess(User user, String taskName) {
        final Task task = getByName(taskName);
        if (!task.getOwner().equals(user)) {
            throw new ServiceException(ServiceException.ErrorType.ACCESS_DENIED);
        }
        return task;
    }
}
