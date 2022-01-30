package com.example.demo.service.commandExecutor;

import ch.qos.logback.classic.Logger;
import com.example.demo.entity.*;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommandExecutor;
import com.example.demo.service.specificCheckers.UserNameChecker;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Выполнение задачи CREATE_TASK
 */


public class CreateExecute implements CommandExecutor {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public CreateExecute(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(UserNameChecker.class);

    /**
     * @param request - запрос
     * @return result - результат выполнения запроса
     */

    @Override
    public String execute(Request request) {
        logger.info(">>CreateExecute execute request={}", request);
        Task task = new Task();
        task.setName(request.getAdditionalParam());
        task.setStatus(Status.CREATED);
        task.setUserName(request.getUserName());
        taskRepository.addTask(task);
        // проверка на наличие юзера и создание нового, если такого нет
        Optional<User> optionalUser = userRepository.getUserByName(request.getUserName());
        optionalUser.ifPresentOrElse(user -> user.addTaskName(request.getAdditionalParam()), () -> {
            ArrayList<String> taskName = new ArrayList<>();
            taskName.add(request.getAdditionalParam());
            userRepository.addUser(new User(request.getUserName(), taskName));
        });
        String result = Result.CREATED.name();
        logger.info("<<CreateExecute execute result={}", result);
        return result;
    }
}
