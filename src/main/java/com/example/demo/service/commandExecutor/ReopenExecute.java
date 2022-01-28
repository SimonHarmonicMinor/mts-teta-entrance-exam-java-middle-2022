package com.example.demo.service.commandExecutor;

import com.example.demo.entity.*;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.CommandExecutor;

/**
 * Выполнение задачи REOPEN_TASK
 */

public class ReopenExecute implements CommandExecutor {

    TaskRepository taskRepository;

    @Override
    public String execute(Request request) {
        Task currentTask = taskRepository.getTaskByName(request.getAdditionalParam());
        if (currentTask.getStatus().equals(Status.CLOSED)) {
            Task task = taskRepository.getTaskByName(request.getAdditionalParam());
            task.setStatus(Status.CREATED);
            taskRepository.updateTask(task);
            return Result.REOPENED.name();
        } else return Result.ERROR.name();
    }
}
