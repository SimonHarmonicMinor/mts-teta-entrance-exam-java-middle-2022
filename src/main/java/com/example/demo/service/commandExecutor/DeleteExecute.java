package com.example.demo.service.commandExecutor;

import com.example.demo.entity.Request;
import com.example.demo.entity.Result;
import com.example.demo.entity.Status;
import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.CommandExecutor;

import java.util.Optional;

/**
 * Выполнение задачи DELETE_TASK
 */

public class DeleteExecute implements CommandExecutor {

    private final TaskRepository taskRepository;

    public DeleteExecute(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public String execute(Request request) {
        Optional<Task> currentOptionalTask = taskRepository.getTaskByName(request.getAdditionalParam());
        String result = currentOptionalTask
                .filter(task -> !Status.CLOSED.equals(task.getStatus()))
                .map(task -> {
                    task.setStatus(Status.DELETED);
                    taskRepository.updateTask(task);
                    return Result.DELETED.name();
                }).orElse(Result.ERROR.name());
        return result;

//        if (currentTask.getStatus().equals(Status.CLOSED)) {
//            currentTask.setStatus(Status.DELETED);
//            taskRepository.updateTask(currentTask);
//            return Result.DELETED.name();
//        } else return Result.ERROR.name();
    }
}
