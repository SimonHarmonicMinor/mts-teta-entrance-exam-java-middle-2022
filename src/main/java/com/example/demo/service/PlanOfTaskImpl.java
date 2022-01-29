package com.example.demo.service;

import com.example.demo.entity.Request;

/**
 * Проверка запроса и вызов команды
 */

public class PlanOfTaskImpl implements PlanOfTask {

    private final CommandExecutor commandExecutor;
    private final RequestChecker requestChecker;

    public PlanOfTaskImpl(CommandExecutor commandExecutor, RequestChecker requestChecker) {
        this.commandExecutor = commandExecutor;
        this.requestChecker = requestChecker;
    }

    public String execute(Request request) {
        requestChecker.check(request);
        String result = commandExecutor.execute(request);
        return result;
    }

}
