package com.example.demo.services;

import com.example.demo.models.Command;
import com.example.demo.models.Response;
import com.example.demo.models.User;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.TaskRepositoryImpl;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.UserRepositoryImpl;

public class CommandProcessorImpl implements CommandProcessor {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final TaskRepository taskRepository = new TaskRepositoryImpl();

    @Override
    public Response process(Command command, User user, String arg) {

        return null;
    }
}
