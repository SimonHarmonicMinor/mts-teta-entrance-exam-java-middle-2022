package com.example.demo.service;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.entity.Result;

import java.util.Map;

public class BaseCommandExecutor implements CommandExecutor{

    private Map<Command, CommandExecutor> specificCommandExecutors;

    @Override
    public Result execute(Request request) {
        CommandExecutor commandExecutor = specificCommandExecutors.get(request.getCommand());
        Result result = commandExecutor.execute(request);
        return result;
    }
}
