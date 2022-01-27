package com.example.demo;

import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

public class Storage {

    private final UserRepository userRepository = new UserRepository();
    private final TaskRepository taskRepository = new TaskRepository();

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }
}
