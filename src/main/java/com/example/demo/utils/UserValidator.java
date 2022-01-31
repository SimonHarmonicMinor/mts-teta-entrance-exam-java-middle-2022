package com.example.demo.utils;

import com.example.demo.entity.RequestEntity;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.exception.OtherErrorException;
import com.example.demo.repo.TaskRepository;
import com.example.demo.repo.UserRepository;


public class UserValidator {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public UserValidator(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public boolean isValid(RequestEntity entity) {

        User user = userRepository.getUser(entity.getUserName());
        Task task = taskRepository.getEntity(entity.getName());

        return task.getUserName().equals(user.getNikName());
    }

    public boolean isExist(String name) {

        if (!userRepository.presenceUser(name)) {
            throw new OtherErrorException();
        }

        return true;
    }

    public boolean taskValid(String taskName) {

        Task task = taskRepository.getEntity(taskName);
        if (task != null) {
            throw new OtherErrorException();
        }

        return true;
    }

}
