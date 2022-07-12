package com.example.demo.api.repository;

import com.example.demo.model.Task;

import java.util.List;

public interface ITaskRepository {

    List<Task> findAll();

    int size();

    void add(Task task);

    void remove(Task task);

    void clear();

    Task findOneByName(String name);

    Task removeOneByName(String name);

    List<Task> findAllByUser(String user);

    List<Task> removeAllByUser(String user);

}
