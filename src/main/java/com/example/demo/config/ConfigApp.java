package com.example.demo.config;

import com.example.demo.service.*;

public class ConfigApp {

    public PlanOfTask getPlanOfTask() {
        CommandExecutor commandExecutor = getCommandExecutor();
        RequestChecker requestChecker = getRequestChecker();
        return new PlanOfTaskImpl(commandExecutor, requestChecker);
    }

    public CommandExecutor getCommandExecutor() {
        return new BaseCommandExecutor();
    }

    public RequestChecker getRequestChecker() {
        return new RequestCheckerImpl();
    }

}
