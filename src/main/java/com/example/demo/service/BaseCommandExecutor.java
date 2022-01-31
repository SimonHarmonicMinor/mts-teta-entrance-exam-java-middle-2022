package com.example.demo.service;

import ch.qos.logback.classic.Logger;
import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.service.specificCheckers.UserNameChecker;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Выбор команды для выполнения
 */
public class BaseCommandExecutor implements CommandExecutor{

    private final Map<Command, CommandExecutor> specificCommandExecutors;

    public BaseCommandExecutor(Map<Command, CommandExecutor> specificCommandExecutors) {
        this.specificCommandExecutors = specificCommandExecutors;
    }

    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(UserNameChecker.class);

    @Override
    public String execute(Request request) {
        logger.info(">>BaseCommandExecutor execute request={}", request);
        CommandExecutor commandExecutor = specificCommandExecutors.get(request.getCommand());
        String result = commandExecutor.execute(request);
        logger.info("<<BaseCommandExecutor execute result={}", result);
        return result;
    }
}
