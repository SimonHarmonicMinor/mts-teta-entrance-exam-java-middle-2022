package com.example.demo.service;

import ch.qos.logback.classic.Logger;
import com.example.demo.entity.Request;
import com.example.demo.exception.ExceptionHandler;
import com.example.demo.service.specificCheckers.UserNameChecker;
import org.slf4j.LoggerFactory;

/**
 * Проверка запроса и вызов команды
 */
public class PlanOfTaskImpl implements PlanOfTask {

    private final CommandExecutor commandExecutor;
    private final RequestChecker requestChecker;
    private final ExceptionHandler exceptionHandler;

    public PlanOfTaskImpl(CommandExecutor commandExecutor, RequestChecker requestChecker, ExceptionHandler exceptionHandler) {
        this.commandExecutor = commandExecutor;
        this.requestChecker = requestChecker;
        this.exceptionHandler = exceptionHandler;
    }

    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(UserNameChecker.class);

    public String execute(Request request) {
        logger.info(">>PlanOfTaskImpl execute request={}", request);
        try {
            requestChecker.check(request);
            String result = commandExecutor.execute(request);
            logger.info("<<PlanOfTaskImpl execute result={}", result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return exceptionHandler.handle(e);
        }
    }

}
