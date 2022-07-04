package com.example.demo.api.service;

import com.example.demo.enumerated.Status;
import com.example.demo.model.Task;

import java.util.List;

public interface ITaskService {

    List<Task> findAll();

    Task add(String name, String user);

    void add(Task task);

    void remove(Task task);

    void clear();

    Task findOneByName(String name);

    Task removeOneByName(String name);

    Task changeTaskStatusByName(String name, Status status);

    List<Task> findAllByUser(String user);

}
