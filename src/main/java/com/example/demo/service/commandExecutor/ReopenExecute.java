package com.example.demo.service.commandExecutor;

import com.example.demo.entity.*;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.CommandExecutor;

import java.util.Optional;

/**
 * Выполнение задачи REOPEN_TASK
 */

public class ReopenExecute implements CommandExecutor {

    private final TaskRepository taskRepository;

    public ReopenExecute(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public String execute(Request request) {
        Optional<Task> currentOptionalTask = taskRepository.getTaskByName(request.getAdditionalParam());
        String result = currentOptionalTask
                .filter(task -> Status.CLOSED.equals(task.getStatus()))
                .map(task -> {
                    task.setStatus(Status.CREATED);
                    taskRepository.updateTask(task);
                    return Result.REOPENED.name();
                }).orElse(Result.ERROR.name());
        return result;
//        if (currentTask.getStatus().equals(Status.CLOSED)) {
//            Task task = taskRepository.getTaskByName(request.getAdditionalParam());
//            task.setStatus(Status.CREATED);
//            taskRepository.updateTask(task);
//            return Result.REOPENED.name();
//        } else return Result.ERROR.name();
    }
}
