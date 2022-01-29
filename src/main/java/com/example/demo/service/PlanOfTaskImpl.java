package com.example.demo.service;

import com.example.demo.entity.Request;
import com.example.demo.exception.ExceptionHandler;

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

    public String execute(Request request) {
        try {
            requestChecker.check(request);
            return commandExecutor.execute(request);
        } catch (Exception e) {
            return exceptionHandler.handle(e);
        }
    }

}
