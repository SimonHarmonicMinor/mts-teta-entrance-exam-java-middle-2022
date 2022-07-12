package com.example.demo.repository;

import com.example.demo.api.repository.ITaskRepository;
import com.example.demo.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository implements ITaskRepository {

    private final List<Task> tasks = new ArrayList<>();

    @Override
    public List<Task> findAll() {
        return this.tasks;
    }

    @Override
    public int size() {
        return tasks.size();
    }

    @Override
    public void add(final Task task) {
        tasks.add(task);
    }

    @Override
    public void remove(final Task task) {
        tasks.remove(task);
    }

    @Override
    public void clear() {
        tasks.clear();
    }

    @Override
    public Task findOneByName(final String name) {
        for (final Task task : tasks) {
            if (name.equals(task.getName()))
                return task;
        }
        return null;
    }

    @Override
    public Task removeOneByName(final String name) {
        final Task task = findOneByName(name);
        if (task == null)
            return null;
        tasks.remove(task);
        return task;
    }

    @Override
    public List<Task> findAllByUser(final String user) {
        final List<Task> taskList = new ArrayList<>();
        for (Task task : tasks) {
            if (user.equals(task.getUser()))
                taskList.add(task);
        }
        return taskList;
    }

    @Override
    public List<Task> removeAllByUser(final String user) {
        for (Task task : tasks) {
            if (user.equals(task.getUser()))
                tasks.clear();
        }
        return null;
    }

}
