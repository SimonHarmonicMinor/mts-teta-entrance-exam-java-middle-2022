package com.example.demo.service.commandExecutor;

import com.example.demo.entity.*;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.CommandExecutor;

/**
 * Выполнение задачи CLOSE_TASK
 */

public class CloseExecute implements CommandExecutor {

   private final TaskRepository taskRepository;

    public CloseExecute(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public String execute(Request request) {
        Task currentTask = taskRepository.getTaskByName(request.getAdditionalParam());
        if (!currentTask.getStatus().equals(Status.DELETED)) {
            currentTask.setStatus(Status.CLOSED);
            taskRepository.updateTask(currentTask);
            return Result.CLOSED.name();
        } else return Result.ERROR.name();
    }
}
