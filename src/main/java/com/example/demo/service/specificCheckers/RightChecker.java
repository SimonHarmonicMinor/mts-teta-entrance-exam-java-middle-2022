package com.example.demo.service.specificCheckers;

import ch.qos.logback.classic.Logger;
import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.entity.Task;
import com.example.demo.exception.RightException;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.RequestChecker;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(UserNameChecker.class);

    @Override
    public void check(Request request) {
        logger.info(">>RightChecker check request={}", request);
        String currentUserName = request.getUserName();
        String taskName = request.getAdditionalParam();
        if (Command.CLOSE_TASK.equals(request.getCommand()) || Command.REOPEN_TASK.equals(request.getCommand())
                || Command.DELETE_TASK.equals(request.getCommand())) {
            Optional<Task> optionalTask = taskRepository.getTaskByName(taskName);
            optionalTask.filter(task -> !currentUserName.equals(task.getUserName()))
                    .ifPresent(task -> {
                        throw new RightException("Пользователь не имеет права на совершение действия");
                    });
        }
    }
}
