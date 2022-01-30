package com.example.demo.config;

import com.example.demo.adapter.PlanOfTaskAdapter;
import com.example.demo.entity.Command;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
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
import java.util.function.Supplier;

import static java.util.Optional.ofNullable;

public class ConfigApp {

    private static final Map<String, Object> objectContainer = new HashMap<>();

    public static void initTaskDataBase(List<Task> tasks) {
        for (Task task : tasks) {
            getTaskRepository().addTask(task);
        }
    }

    public static void initUserDataBase(List<User> users) {
        for (User user : users) {
            getUserRepository().addUser(user);
        }
    }

    private static PlanOfTask getPlanOfTask() {
        CommandExecutor commandExecutor = getCommandExecutor();
        RequestChecker requestChecker = getRequestChecker();
        ExceptionHandler exceptionHandler = getExceptionHandler();
        return (PlanOfTaskImpl) getOrCreate(PlanOfTaskImpl.class.getCanonicalName(), () -> new PlanOfTaskImpl(commandExecutor, requestChecker, exceptionHandler));
    }

    private static CommandExecutor getCommandExecutor() {
        return (BaseCommandExecutor) getOrCreate(BaseCommandExecutor.class.getCanonicalName(), () -> new BaseCommandExecutor(getSpecificCommandExecutors()));
    }

    private static ExceptionHandler getExceptionHandler() {
        return (ExceptionHandlerImpl) getOrCreate(ExceptionHandlerImpl.class.getCanonicalName(), ExceptionHandlerImpl::new);
    }

    private static List<RequestChecker> getRequestCheckerList() {
        List<RequestChecker> requestCheckerList = new ArrayList<>();
        requestCheckerList.add(getRequiredFieldsChecker());
        requestCheckerList.add(getCommandNameChecker());
        requestCheckerList.add(getUserNameChecker());
        requestCheckerList.add(getTaskChecker());
        requestCheckerList.add(getRightChecker());
        requestCheckerList.add(getAdditionalParamChecker());
        requestCheckerList.add(getTaskNameChecker());
        return requestCheckerList;
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
        return (CreateExecute) getOrCreate(CreateExecute.class.getCanonicalName(), () -> new CreateExecute(getTaskRepository()));
    }

    private static CommandExecutor getCloseExecute() {
        return (CloseExecute) getOrCreate(CloseExecute.class.getCanonicalName(), () -> new CloseExecute(getTaskRepository()));
    }

    private static CommandExecutor getDeleteExecute() {
        return (DeleteExecute) getOrCreate(DeleteExecute.class.getCanonicalName(), () -> new DeleteExecute(getTaskRepository()));
    }

    private static CommandExecutor getListExecute() {
        return (ListExecute) getOrCreate(ListExecute.class.getCanonicalName(), () -> new ListExecute(getUserRepository()));
    }

    private static CommandExecutor getReopenExecute() {
        return (ReopenExecute) getOrCreate(ReopenExecute.class.getCanonicalName(), () -> new ReopenExecute(getTaskRepository()));
    }

    private static RequestChecker getRequestChecker() {
        return (RequestCheckerImpl) getOrCreate(RequestCheckerImpl.class.getCanonicalName(), () -> new RequestCheckerImpl(getRequestCheckerList()));
    }

    private static RequestChecker getAdditionalParamChecker() {
        return (AdditionalParamChecker) getOrCreate(AdditionalParamChecker.class.getCanonicalName(), AdditionalParamChecker::new);
    }

    private static RequestChecker getCommandNameChecker() {
        return (CommandNameChecker) getOrCreate(CommandNameChecker.class.getCanonicalName(), CommandNameChecker::new);
    }

    private static RequestChecker getRequiredFieldsChecker() {
        return (RequiredFieldsChecker) getOrCreate(RequiredFieldsChecker.class.getCanonicalName(), RequiredFieldsChecker::new);
    }

    private static RequestChecker getTaskChecker() {
        return (TaskChecker) getOrCreate(TaskChecker.class.getCanonicalName(), () -> new TaskChecker(getUserRepository()));
    }

    private static RequestChecker getRightChecker() {
        return (RightChecker) getOrCreate(RightChecker.class.getCanonicalName(), () -> new RightChecker(getTaskRepository()));
    }

    private static RequestChecker getTaskNameChecker() {
        return (TaskNameChecker) getOrCreate(TaskNameChecker.class.getCanonicalName(), () -> new TaskNameChecker(getTaskRepository()));
    }

    private static RequestChecker getUserNameChecker() {
        return (UserNameChecker) getOrCreate(UserNameChecker.class.getCanonicalName(), UserNameChecker::new);
    }

    private static UserRepository getUserRepository() {
        return (UserRepositoryImpl) getOrCreate(UserRepositoryImpl.class.getCanonicalName(), UserRepositoryImpl::new);
    }

    private static TaskRepository getTaskRepository() {
        return (TaskRepositoryImpl) getOrCreate(TaskRepositoryImpl.class.getCanonicalName(), TaskRepositoryImpl::new);
    }

    private static Object getOrCreate(String objectName, Supplier<Object> initFunc) {
        return ofNullable(objectContainer.get(objectName))
                .orElseGet(() -> {
                    objectContainer.put(objectName, initFunc.get());
                    return objectContainer.get(objectName);
                });
    }

    public static PlanOfTaskAdapter getPlanOfTaskAdapter() {
        return (PlanOfTaskAdapter) getOrCreate(PlanOfTaskAdapter.class.getCanonicalName(), () -> new PlanOfTaskAdapter(getPlanOfTask(), getExceptionHandler()));
    }
}