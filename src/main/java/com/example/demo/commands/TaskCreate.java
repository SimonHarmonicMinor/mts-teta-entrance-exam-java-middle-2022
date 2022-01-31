package com.example.demo.commands;

import com.example.demo.entity.RequestEntity;
import com.example.demo.entity.Status;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.exception.OtherErrorException;
import com.example.demo.repo.TaskRepository;
import com.example.demo.repo.UserRepository;
import com.example.demo.utils.UserValidator;

public class TaskCreate implements CommandInterface {

    private final String name = "CREATE_TASK";

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public TaskCreate(TaskRepository taskRepository, UserRepository userRepository, UserValidator userValidator) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String execute(RequestEntity request) {

        try {
            userValidator.isExist(request.getUserName());
            userValidator.taskValid(request.getName());


            Task task = new Task();
            task.setName(request.getName());
            taskRepository.create(task);

            User user = userRepository.getUser(request.getUserName());
            user.addNewTask(task.getName());
        } catch (OtherErrorException ex) {
            return ex.getMessage();
        }

        return Status.CREATED.toString();
    }
}