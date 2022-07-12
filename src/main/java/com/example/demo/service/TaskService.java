package com.example.demo.service;

import com.example.demo.api.repository.ITaskRepository;
import com.example.demo.api.service.ITaskService;
import com.example.demo.enumerated.Status;
import com.example.demo.model.Task;
import com.example.demo.util.ValidationUtil;

import java.util.List;

public class TaskService implements ITaskService {

    private final ITaskRepository taskRepository;

    public TaskService(final ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task add(final String name, final String user) {
        if (ValidationUtil.isEmpty(name) || ValidationUtil.isEmpty(user))
            return null;
        final Task task = new Task(name, user);
        add(task);
        return task;
    }

    @Override
    public void add(final Task task) {
        if (task == null)
            return;
        taskRepository.add(task);
    }

    @Override
    public void remove(final Task task) {
        if (task == null)
            return;
        taskRepository.remove(task);
    }

    @Override
    public void clear() {
        taskRepository.clear();
    }

    @Override
    public Task findOneByName(String name) {
        if (ValidationUtil.isEmpty(name))
            return null;
        return taskRepository.findOneByName(name);
    }

    @Override
    public Task removeOneByName(String name) {
        if (ValidationUtil.isEmpty(name))
            return null;
        return taskRepository.removeOneByName(name);
    }

    @Override
    public Task changeTaskStatusByName(String name, Status status) {
        if (ValidationUtil.isEmpty(name) || status == null)
            return null;
        final Task task = findOneByName(name);
        if (task == null)
            return null;
        task.setStatus(status);
        return task;
    }

    public List<Task> findAllByUser(String user)  {
        if (ValidationUtil.isEmpty(user))
            return null;
        return taskRepository.findAllByUser(user);
    }

}
