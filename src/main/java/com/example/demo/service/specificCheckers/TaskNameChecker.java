package com.example.demo.service.specificCheckers;

import ch.qos.logback.classic.Logger;
import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.ErrorException;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.RequestChecker;
import org.slf4j.LoggerFactory;

/**
 * Проверка имени создаваемой задачи на уникальность
 */
public class TaskNameChecker implements RequestChecker {

    private final TaskRepository taskRepository;

    public TaskNameChecker(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(UserNameChecker.class);

    @Override
    public void check(Request request) {
        logger.info(">>TaskNameChecker check request={}", request);
        if (request.getCommand().equals(Command.CREATE_TASK)) {
            if (taskRepository.getTaskByName(request.getAdditionalParam()).isPresent()) {
                throw new ErrorException("Имя задачи не уникально");
            }
        }
    }
}
