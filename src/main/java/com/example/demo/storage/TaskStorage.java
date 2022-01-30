package com.example.demo.storage;

import com.example.demo.model.Task;
import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface TaskStorage {
    void save(Task task);
    void delete(Task task);
    boolean existsByName(String taskName);
    Optional<Task> findByName(String taskName);
    List<Task> listAll(User sender, User owner);
}
