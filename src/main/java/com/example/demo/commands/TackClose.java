package com.example.demo.commands;

import com.example.demo.entity.RequestEntity;
import com.example.demo.entity.Status;
import com.example.demo.exception.OtherErrorException;
import com.example.demo.repo.TaskRepository;
import com.example.demo.utils.UserValidator;


public class TackClose implements CommandInterface {

    private final String name = "CLOSE_TASK";

    private final TaskRepository taskRepository;
    private final UserValidator userValidator;

    public TackClose(TaskRepository taskRepository, UserValidator userValidator) {
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
            userValidator.isValid(request);

            taskRepository.close(request.getName());
        } catch (OtherErrorException ex) {
            return ex.getMessage();
        }

        return Status.CLOSED.name();
    }
}
