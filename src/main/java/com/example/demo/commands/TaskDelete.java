package com.example.demo.commands;

import com.example.demo.entity.RequestEntity;
import com.example.demo.entity.Status;
import com.example.demo.exception.AccessDeniedException;
import com.example.demo.exception.OtherErrorException;
import com.example.demo.repo.TaskRepository;
import com.example.demo.utils.UserValidator;


public class TaskDelete implements CommandInterface {

    private final String name = "DELETE_TASK";
    private final TaskRepository taskRepository;
    private final UserValidator userValidator;

    public TaskDelete(TaskRepository taskRepository, UserValidator userValidator) {
        this.taskRepository = taskRepository;
        this.userValidator = userValidator;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String execute(RequestEntity request) {

        try {
            if (!userValidator.isValid(request)) {
                throw new AccessDeniedException();
            }

            taskRepository.remove(request.getName());
        } catch (AccessDeniedException | OtherErrorException ex) {
            return ex.getMessage();
        }

        return Status.DELETED.toString();
    }
}
