package com.example.demo.repository;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TasksRepository {
    Optional<Task> findFirstByName(String name);
    Optional<Task> findFirstByNameAndStateNot(String name, Task.State state);
    List<Task> findAll();
    List<Task> findAllByStateIn(Collection<Task.State> states);
    List<Task> findAllByStateNot(Task.State state);
    List<Task> findAllByUser(User user);
    List<Task> findAllByUserAndStateNot(User user, Task.State state);
    void save(Task task);
}
