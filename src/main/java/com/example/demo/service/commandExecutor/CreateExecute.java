package com.example.demo.service.commandExecutor;

import com.example.demo.entity.*;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.CommandExecutor;

/**
 * Выполнение задачи CREATE_TASK
 */


public class CreateExecute implements CommandExecutor {

    TaskRepository taskRepository;

    @Override
    public Result execute(Request request) {
        if (request.getCommand().equals(Command.CREATE_TASK)) {
            Task task = new Task();
            task.setName(request.getAdditionalParam());
            task.setStatus(Status.CREATED);
            task.setName(request.getUserName());
            taskRepository.addTask(task);
        }
        return Result.CREATED;
    }
}
