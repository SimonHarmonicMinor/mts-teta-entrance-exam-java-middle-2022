package com.example.demo.service.commandExecutor;

import com.example.demo.entity.*;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommandExecutor;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Выполнение задачи CLOSE_TASK
 */

public class CloseExecute implements CommandExecutor {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public CloseExecute(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String execute(Request request) {
        Optional<Task> currentOptionalTask = taskRepository.getTaskByName(request.getAdditionalParam());
        String result = currentOptionalTask
                .filter(task -> !Status.DELETED.equals(task.getStatus()))
                .map(task -> {
                    task.setStatus(Status.CLOSED);
                    taskRepository.updateTask(task);
                    return Result.CLOSED.name();
                })
//               .orElseGet(() -> {
//                   return Result.ERROR.name();
//               });
                .orElse(Result.ERROR.name());

            return result;
    }
}
