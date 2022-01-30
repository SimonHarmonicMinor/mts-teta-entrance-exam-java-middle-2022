package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.entity.Task;
import com.example.demo.exception.RightException;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.RequestChecker;

import java.util.Optional;

/**
 * Проверка пользователя на право совершать операции над задачей.
 * Пользователь должен быть создателем данной задачи.
 */

public class RightChecker implements RequestChecker {

    private final TaskRepository taskRepository;

    public RightChecker(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void check(Request request) {
        String currentUserName = request.getUserName();
        String taskName = request.getAdditionalParam();

        if (request.getCommand().equals(Command.CLOSE_TASK) || request.getCommand().equals(Command.REOPEN_TASK)
                || request.getCommand().equals(Command.DELETE_TASK)) {
            Optional<Task> optionalTask = taskRepository.getTaskByName(taskName);
            optionalTask.filter(task -> !currentUserName.equals(task.getUserName()))
                    .orElseThrow(() -> new RightException("Пользователь не имеет права на совершение действия"));
        }

//        Task task = taskRepository.getTaskByName(taskName);
//        if (!currentUserName.equals(task.getUserName())) {
//            throw new RightException("Пользователь не имеет права на совершение действия");
//        }

    }
}
