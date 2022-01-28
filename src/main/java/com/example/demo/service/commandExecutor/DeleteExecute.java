package com.example.demo.service.commandExecutor;

import com.example.demo.entity.Request;
import com.example.demo.entity.Result;
import com.example.demo.entity.Status;
import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.CommandExecutor;

/**
 * Выполнение задачи DELETE_TASK
 */

public class DeleteExecute implements CommandExecutor {

    TaskRepository taskRepository;

    @Override
    public String execute(Request request) {
        Task currentTask = taskRepository.getTaskByName(request.getAdditionalParam());
        if (currentTask.getStatus().equals(Status.CLOSED)) {
            currentTask.setStatus(Status.DELETED);
            taskRepository.updateTask(currentTask);
            return Result.DELETED.name();
        } else return Result.ERROR.name();
    }
}
