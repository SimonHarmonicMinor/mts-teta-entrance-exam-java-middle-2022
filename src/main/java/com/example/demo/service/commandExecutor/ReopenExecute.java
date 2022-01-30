package com.example.demo.service.commandExecutor;

import ch.qos.logback.classic.Logger;
import com.example.demo.entity.Request;
import com.example.demo.entity.Result;
import com.example.demo.entity.Status;
import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.CommandExecutor;
import com.example.demo.service.specificCheckers.UserNameChecker;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Выполнение задачи REOPEN_TASK
 */

public class ReopenExecute implements CommandExecutor {

    private final TaskRepository taskRepository;

    public ReopenExecute(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(UserNameChecker.class);

    /**
     * @param request - запрос
     * @return result - результат выполнения запроса
     */

    @Override
    public String execute(Request request) {
        logger.info(">>ReopenExecute execute request={}", request);
        Optional<Task> currentOptionalTask = taskRepository.getTaskByName(request.getAdditionalParam());
        String result = currentOptionalTask
                .filter(task -> Status.CLOSED.equals(task.getStatus()))
                .map(task -> {
                    task.setStatus(Status.CREATED);
                    taskRepository.updateTask(task);
                    return Result.REOPENED.name();
                }).orElse(Result.ERROR.name());
        logger.info("<<ReopenExecute execute result={}", result);
        return result;
    }
}
