package com.example.demo.config;

import com.example.demo.entity.Command;
import com.example.demo.exception.ExceptionHandler;
import com.example.demo.exception.ExceptionHandlerImpl;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TaskRepositoryImpl;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRepositoryImpl;
import com.example.demo.service.*;
import com.example.demo.service.commandExecutor.*;
import com.example.demo.service.specificCheckers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigApp {

    public PlanOfTask getPlanOfTask() {
        CommandExecutor commandExecutor = getCommandExecutor();
        RequestChecker requestChecker = getRequestChecker();
        ExceptionHandler exceptionHandler = getExceptionHandler();
        return new PlanOfTaskImpl(commandExecutor, requestChecker, exceptionHandler);
    }

    private static CommandExecutor getCommandExecutor() {
        return new BaseCommandExecutor(getSpecificCommandExecutors());
    }

    private static ExceptionHandler getExceptionHandler() {
        return new ExceptionHandlerImpl();
    }

    private static Map<Command, CommandExecutor> getSpecificCommandExecutors() {
        Map<Command, CommandExecutor> specificCommandExecutors = new HashMap<>();
        specificCommandExecutors.put(Command.CREATE_TASK, getCreateExecute());
        specificCommandExecutors.put(Command.CLOSE_TASK, getCloseExecute());
        specificCommandExecutors.put(Command.DELETE_TASK, getDeleteExecute());
        specificCommandExecutors.put(Command.LIST_TASK, getListExecute());
        specificCommandExecutors.put(Command.REOPEN_TASK, getReopenExecute());
        return specificCommandExecutors;
    }

    private static CommandExecutor getCreateExecute() {
        return new CreateExecute(getTaskRepository());
    }

    private static CommandExecutor getCloseExecute() {
        return new CloseExecute(getTaskRepository());
    }

    private static CommandExecutor getDeleteExecute() {
        return new DeleteExecute(getTaskRepository());
    }

    private static CommandExecutor getListExecute() {
        return new ListExecute(getUserRepository());
    }

    private static CommandExecutor getReopenExecute() {
        return new ReopenExecute(getTaskRepository());
    }

    private static RequestChecker getRequestChecker() {
        return new RequestCheckerImpl(getRequestCheckerList());
    }

    private static List<RequestChecker> getRequestCheckerList() {
        List<RequestChecker> requestCheckerList = new ArrayList<>();
        requestCheckerList.add(getAdditionalParamChecker());
        requestCheckerList.add(getCommandNameChecker());
        requestCheckerList.add(getRequiredFieldsChecker());
        requestCheckerList.add(getRightChecker());
        requestCheckerList.add(getTaskNameChecker());
        requestCheckerList.add(getUserNameChecker());
        return requestCheckerList;
    }

    private static RequestChecker getAdditionalParamChecker() {
        return new AdditionalParamChecker();
    }

    private static RequestChecker getCommandNameChecker() {
        return new CommandNameChecker();
    }

    private static RequestChecker getRequiredFieldsChecker() {
        return new RequiredFieldsChecker();
    }

    private static RequestChecker getRightChecker() {
        return new RightChecker(getUserRepository());
    }

    private static RequestChecker getTaskNameChecker() {
        return new TaskNameChecker(getTaskRepository());
    }

    private static RequestChecker getUserNameChecker() {
        return new UserNameChecker();
    }

    private static UserRepository getUserRepository() {
        return new UserRepositoryImpl();
    }

    private static TaskRepository getTaskRepository() {
        return new TaskRepositoryImpl();
    }

}
