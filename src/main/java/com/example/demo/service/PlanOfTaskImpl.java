package com.example.demo.service;

import com.example.demo.entity.Request;

/**
 * Проверка запроса и вызов команды
 */

public class PlanOfTaskImpl implements PlanOfTask {

    private CommandExecutor commandExecutor;
    private RequestChecker requestChecker;

    public String execute(Request request) {
        requestChecker.check(request);
        String result = commandExecutor.execute(request);
        return result;
    }

}
