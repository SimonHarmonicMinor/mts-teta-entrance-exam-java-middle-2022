package com.example.demo.repository;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;

import java.util.*;
import java.util.stream.Collectors;

public class TasksRepository {
    private final Set<Task> tasks = new HashSet<>();

    public Optional<Task> findFirstByName(String name) {
        return tasks.stream().filter(t -> t.getName().equals(name)).findFirst();
    }

    public Optional<Task> findFirstByNameAndStateNot(String name, Task.State state) {
        return tasks.stream().filter(t -> t.getName().equals(name) && !t.getState().equals(state)).findFirst();
    }

    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }

    public List<Task> findAllByStateIn(Collection<Task.State> states) {
        return tasks.stream().filter(t -> states.contains(t.getState())).collect(Collectors.toList());
    }

    public List<Task> findAllByStateNot(Task.State state) {
        return tasks.stream().filter(t -> !t.getState().equals(state)).collect(Collectors.toList());
    }

    public List<Task> findAllByUser(User user) {
        return tasks.stream().filter(t -> t.getUser().equals(user)).collect(Collectors.toList());
    }

    public List<Task> findAllByUserAndStateNot(User user, Task.State state) {
        return tasks.stream().filter(t -> t.getUser().equals(user) && !t.getState().equals(state)).collect(Collectors.toList());
    }

    public void save(Task task) {
        Optional<Task> existingTask = tasks.stream().filter(t -> t.equals(task)).findFirst();
        if(existingTask.isPresent()) {
            existingTask.get().setFields(task);
            return;
        }
        tasks.add(task);
    }
}
