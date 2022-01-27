package com.example.demo.service.commandService;

import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TaskRepositoryImpl;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRepositoryImpl;

abstract class AbstractCommandService implements CommandService {
    protected TaskRepository taskRepository;
    protected UserRepository userRepository;

    public AbstractCommandService() {
        this.taskRepository = new TaskRepositoryImpl();
        this.userRepository = new UserRepositoryImpl();
    }
}
