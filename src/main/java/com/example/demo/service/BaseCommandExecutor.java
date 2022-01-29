package com.example.demo.service;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;

import java.util.Map;

/**
 * Выбор команды для выполнения
 */

public class BaseCommandExecutor implements CommandExecutor{

    private final Map<Command, CommandExecutor> specificCommandExecutors;

    public BaseCommandExecutor(Map<Command, CommandExecutor> specificCommandExecutors) {
        this.specificCommandExecutors = specificCommandExecutors;
    }

    @Override
    public String execute(Request request) {
        CommandExecutor commandExecutor = specificCommandExecutors.get(request.getCommand());
        String result = commandExecutor.execute(request);
        return result;
    }
}
