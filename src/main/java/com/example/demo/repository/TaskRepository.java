package com.example.demo.repository;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.exception.NonUniqTaskException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TaskRepository {

    private final List<Task> list = Collections.synchronizedList(new ArrayList<>());

    public void create(Task task) throws NonUniqTaskException {
        try {
            findByTitle(task.getTitle());
        } catch (EntityNotFoundException e) {
            list.add(task);
            return;
        }

        throw new NonUniqTaskException(String.format("Task title \"%s\" already taken", task.getTitle()));
    }

    public Stream<Task> getTaskStream() {
        return list.stream().filter(task -> !task.getState().equals(Task.State.DELETED));
    }

    public Task findByTitle(String taskTitle) throws EntityNotFoundException {
        return getTaskStream().filter(task -> task.getTitle().equals(taskTitle)).findAny().orElseThrow(
            () -> new EntityNotFoundException(String.format("Task \"%s\" does not exists", taskTitle))
        );
    }

    /*
     * Possibly place List<Task> refs inside User.tasks
     * Works faster but duplicate the filter of deleted tasks logic.
     */
    public List<Task> getTasksByUser(User user) {
        return getTaskStream().filter(task -> task.getUser().equals(user)).collect(Collectors.toList());
    }
}
