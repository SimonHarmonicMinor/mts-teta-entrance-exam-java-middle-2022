package com.example.demo.logic;

import com.example.demo.model.*;
import com.example.demo.util.RequestParser;
import com.example.demo.util.RequestValidator;
import com.example.demo.util.ResponseFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

// (?) Логика в контроллере или валидация в сервисе+ потребность в DTO между слоями
public class TaskControllerImp implements TaskController {
    private final Logger log = LoggerFactory.getLogger(TaskControllerImp.class);
    private final TaskService taskService;

    public TaskControllerImp(TaskService taskService) {
        this.taskService = taskService;
        log.info("Controller initiated");
    }

    public String handleRequest(String request) {
        System.out.println("\n");
        log.info("Start handleRequest" + " " + request);

        // Базовые проверки (количество аргументов, команда из списка команд)
        log.info("Start parseRequest");
        String[] inputCommand = RequestParser.parse(request);
        log.info("End parseRequest");

        log.info("Start format request validate");
        if (RequestValidator.validateRequest(inputCommand)) {
            log.info("Invalidate format request");
            return ResponseFormatter.createResponse(Result.WRONG_FORMAT);
        }
        log.info("Success format request validate");

        // если запрос списка - сразу запрашиваем + форматируем отображение
        if (inputCommand[1].equals(Command.LIST_TASK.name())) {
            log.info("LIST_TASK command. Start handle");
            List<Task> tasks = taskService.findAllByUser(new User(inputCommand[2]));
            log.info("Tasks by user were collected");

            return ResponseFormatter.createResponse(tasks);
        }

        // для остального -создание, закрытие, открытие, удаление
        return ResponseFormatter.createResponse(handleModifyTask(inputCommand));
    }

    private Result handleModifyTask(String[] inputCommand) {
        log.info("Start handleModifyTask");
        // если создание задачи - проверяем свободное ли название, если да - создаем
        if (inputCommand[1].equals(Command.CREATE_TASK.name())) {
            log.info("Chosen command: create task");

            if (isCreatedTask(inputCommand[2])) {
                log.info("Task name is busy");
                return Result.ERROR;
            }

            taskService.createTask(inputCommand[2], new User(inputCommand[0]));
            return Result.CREATED;
        }

        // для закрытие/открытие/удаление - проверяем наличие записи
        log.info("Check existing task");
        if (!isCreatedTask(inputCommand[2])) {
            log.info("Task not found by task name");
            return Result.ERROR;
        }

        // наличие прав доступа
        log.info("Check access");
        if (!validateAccess(inputCommand[1], new User(inputCommand[0]))) {
            log.info("User is not owner task");
            return Result.ACCESS_DENIED;
        }

        // соответствие статуса задачи для измениня
        log.info("Check task status");
        if (inputCommand[1].equals(Command.REOPEN_TASK.name())) {
            if (validateReopenTask(inputCommand[1])) {
                log.info("Task status doesnot allow reopen task");
                return Result.ERROR;
            }

            log.info("Reopening task");
            taskService.openTask(inputCommand[2]);
            return Result.REOPENED;
        }

        if (inputCommand[1].equals(Command.CLOSE_TASK.name())) {
            if (validateCloseTask(inputCommand[1])) {
                log.info("Task status doesnot allow close task");
                return Result.ERROR;
            }

            log.info("Closing task");
            taskService.closeTask(inputCommand[2]);
            return Result.CLOSED;
        }

        if (inputCommand[1].equals(Command.DELETE_TASK.name())) {
            if (validateDeleteTask(inputCommand[1])) {
                log.info("Task status doesnot allow delete task");
                return Result.ERROR;
            }

            log.info("Deleting task");
            taskService.deleteTask(inputCommand[2]);
            return Result.DELETED;
        }

        log.info("Something was wrong");
        return Result.ERROR;
    }

    private boolean isCreatedTask(String taskName) {
        log.info("Checking task is created");
        return taskService.findByName(taskName) != null;
    }

    private boolean validateAccess(String taskName, User user) {
        log.info("Checking user is task owner");
        if (taskService.findByName(taskName) == null) {
            log.info("Task was not found");
            return false;
        }

        Task task = taskService.findByName(taskName);

        return task.getUser().equals(user);
    }

    private boolean validateReopenTask(String taskName) {
        log.info("Checking status for reopening");
        TaskStatus currentStatus = taskService.findByName(taskName).getStatus();

        return currentStatus.equals(TaskStatus.CLOSED);
    }

    private boolean validateCloseTask(String taskName) {
        log.info("Checking status for closing");
        TaskStatus currentStatus = taskService.findByName(taskName).getStatus();

        return currentStatus.equals(TaskStatus.CREATED);
    }

    private boolean validateDeleteTask(String taskName) {
        log.info("Checking status for deleting");
        TaskStatus currentStatus = taskService.findByName(taskName).getStatus();

        return currentStatus.equals(TaskStatus.CLOSED);
    }
}
