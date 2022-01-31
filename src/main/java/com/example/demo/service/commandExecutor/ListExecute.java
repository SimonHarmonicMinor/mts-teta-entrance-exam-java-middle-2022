package com.example.demo.service.commandExecutor;

import ch.qos.logback.classic.Logger;
import com.example.demo.entity.Request;
import com.example.demo.entity.Result;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommandExecutor;
import com.example.demo.service.specificCheckers.UserNameChecker;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Выполнение задачи LIST_TASK
 */
public class ListExecute implements CommandExecutor {

    private final UserRepository userRepository;

    public ListExecute(UserRepository userRepository) {
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
        logger.info(">>ListExecute execute request={}", request);
        List<String> taskName = userRepository.getUserByName(request.getAdditionalParam())
                .map(User::getTaskName).orElse(List.of());
        String result = Result.TASKS.name() + " " + taskName;
        logger.info("<<ListExecute execute result={}", result);
        return result;
    }
}
