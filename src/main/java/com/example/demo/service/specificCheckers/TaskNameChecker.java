package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.RequestChecker;

import static java.util.Objects.nonNull;

/**
 * Проверка имени создаваемой задачи на уникальность
 */

public class TaskNameChecker implements RequestChecker {

    private final TaskRepository taskRepository;

    public TaskNameChecker(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void check(Request request) {
        if (request.getCommand().equals(Command.CREATE_TASK)) {
            if (nonNull(taskRepository.getTaskByName(request.getAdditionalParam()))) {
                throw new FormatException("Имя задачи не уникально");
            }
        }
    }
}
