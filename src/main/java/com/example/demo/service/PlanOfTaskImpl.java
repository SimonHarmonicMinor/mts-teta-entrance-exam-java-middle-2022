package com.example.demo.service;

import com.example.demo.entity.Request;
import com.example.demo.entity.Result;
import com.example.demo.repository.PlanOfTask;

public class PlanOfTaskImpl implements PlanOfTask {

    private CommandExecutor commandExecutor;
    private RequestChecker requestChecker;

    public Result execute(Request request) {
        requestChecker.check(request);
        commandExecutor.execute(request);
    }

}
