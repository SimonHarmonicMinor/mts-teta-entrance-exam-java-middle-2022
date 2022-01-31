package com.example.demo.repository;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;

import java.util.*;
import java.util.stream.Collectors;

public class TasksRepositoryImpl implements TasksRepository {
    private final Set<Task> tasks = new HashSet<>();

    @Override
    public Optional<Task> findFirstByName(String name) {
        return tasks.stream().filter(t -> t.getName().equals(name)).findFirst();
    }

    @Override
    public Optional<Task> findFirstByNameAndStateNot(String name, Task.State state) {
        return tasks.stream().filter(t -> t.getName().equals(name) && !t.getState().equals(state)).findFirst();
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }

    @Override
    public List<Task> findAllByStateIn(Collection<Task.State> states) {
        return tasks.stream().filter(t -> states.contains(t.getState())).collect(Collectors.toList());
    }

    @Override
    public List<Task> findAllByStateNot(Task.State state) {
        return tasks.stream().filter(t -> !t.getState().equals(state)).collect(Collectors.toList());
    }

    @Override
    public List<Task> findAllByUser(User user) {
        return tasks.stream().filter(t -> t.getUser().equals(user)).collect(Collectors.toList());
    }

    @Override
    public List<Task> findAllByUserAndStateNot(User user, Task.State state) {
        return tasks.stream().filter(t -> t.getUser().equals(user) && !t.getState().equals(state)).collect(Collectors.toList());
    }

    @Override
    public void save(Task task) {
        Optional<Task> existingTask = tasks.stream().filter(t -> t.equals(task)).findFirst();
        if(existingTask.isPresent()) {
            existingTask.get().setFields(task);
            return;
        }
        tasks.add(task);
    }
}
