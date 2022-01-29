package com.example.demo.service.commandExecutor;

import com.example.demo.entity.*;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.CommandExecutor;

/**
 * Выполнение задачи CREATE_TASK
 */


public class CreateExecute implements CommandExecutor {

   private final TaskRepository taskRepository;

    public CreateExecute(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public String execute(Request request) {
        Task task = new Task();
        task.setName(request.getAdditionalParam());
        task.setStatus(Status.CREATED);
        task.setName(request.getUserName());
        taskRepository.addTask(task);
        return Result.CREATED.name();
    }
}
