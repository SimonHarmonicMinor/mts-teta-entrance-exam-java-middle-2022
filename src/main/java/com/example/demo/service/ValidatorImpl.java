package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.repository.TasksRepository;
import com.example.demo.repository.UserRepository;

public class ValidatorImpl implements Validator{

    private final UserRepository userRepository;
    private final TasksRepository tasksRepository;

    public ValidatorImpl(UserRepository userRepository, TasksRepository tasksRepository) {
        this.userRepository = userRepository;
        this.tasksRepository = tasksRepository;
    }

    @Override
    public boolean validateTaskIsAbsent(String taskName) {
        return tasksRepository.findFirstByNameAndStateNot(taskName, Task.State.DELETED).isEmpty();
    }

    @Override
    public boolean validateTaskIsPresent(String taskName) {
        return tasksRepository.findFirstByNameAndStateNot(taskName, Task.State.DELETED).isPresent();
    }

    @Override
    public boolean validateTaskBelongsToUser(Task task, String userName) {
        return task.getUser().getName().equals(userName);
    }

    @Override
    public boolean validateTaskStateMatch(Task task, Task.State state) {
        return task.getState().equals(state);
    }

    @Override
    public boolean validateUserIsPresent(String userName) {
        return userRepository.findFirstByName(userName).isPresent();
    }
}
