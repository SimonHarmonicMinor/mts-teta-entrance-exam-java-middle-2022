package com.example.demo.config;

import com.example.demo.entity.Command;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TaskRepositoryImpl;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRepositoryImpl;
import com.example.demo.service.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigApp {

    public PlanOfTask getPlanOfTask() {
        CommandExecutor commandExecutor = getCommandExecutor();
        RequestChecker requestChecker = getRequestChecker();
        return new PlanOfTaskImpl(commandExecutor, requestChecker);
    }

    private static CommandExecutor getCommandExecutor() {
        return new BaseCommandExecutor(getSpecificCommandExecutors());
    }

    private static Map<Command, CommandExecutor> getSpecificCommandExecutors() {
        return new HashMap<>();
    }

    private static RequestChecker getRequestChecker() {
        return new RequestCheckerImpl(getRequestCheckerList());
    }

    private static List<RequestChecker> getRequestCheckerList() {
        return new ArrayList<>();
    }

    private static UserRepository getUserRepository() {
        return new UserRepositoryImpl();
    }

    private static TaskRepository getTaskRepository() {
        return new TaskRepositoryImpl();
    }

}
