package com.example.demo.service;

import com.example.demo.entity.Request;
import com.example.demo.entity.Result;
import com.example.demo.repository.PlanOfTask;

/**
 * Класс отвечает за проверки запроса и вызов команды
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
