package com.example.demo.commands;

import com.example.demo.entity.RequestEntity;
import com.example.demo.entity.Status;
import com.example.demo.entity.Task;
import com.example.demo.exception.OtherErrorException;
import com.example.demo.repo.TaskRepository;
import com.example.demo.utils.UserValidator;

import java.util.List;
import java.util.stream.Collectors;


public class TaskList implements CommandInterface {

    private final String name = "LIST_TASK";
    private final TaskRepository taskRepository;
    private final UserValidator userValidator;

    public TaskList(TaskRepository taskRepository, UserValidator userValidator) {
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
            userValidator.isExist(request.getName());

        } catch (OtherErrorException ex) {
            return ex.getMessage();
        }

        List<Task> taskList = taskRepository.readAll();
        List<Task> collect = taskList.stream()
                .filter(task -> task.getUserName().equals(request.getName()))
                .collect(Collectors.toList());

        return Status.TASKS.toString() + collect.toString();
    }
}
