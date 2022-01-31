package com.example.demo.logic;

import com.example.demo.model.*;
import com.example.demo.util.RequestParser;
import com.example.demo.util.RequestValidator;
import com.example.demo.util.ResponseFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

// (?) Потребность в DTO между слоями
public class TaskControllerImp implements TaskController {
    private final Logger log = LoggerFactory.getLogger(TaskControllerImp.class);

    private final TaskService taskService;

    public TaskControllerImp(TaskService taskService) {
        this.taskService = taskService;
        log.debug("Controller initiated");
    }

    public String handleRequest(String request) {
        System.out.println("\n");
        log.debug("Start handleRequest" + " " + request);

        // Базовые проверки (количество аргументов, команда из списка команд)
        log.debug("Start parseRequest");
        String[] inputCommand = RequestParser.parse(request);
        log.debug("End parseRequest");

        log.debug("Start format request validate");
        if (RequestValidator.validateRequest(inputCommand)) {
            log.debug("Invalidate format request");
            return ResponseFormatter.createResponse(Result.WRONG_FORMAT);
        }
        log.debug("Success format request validate");

        // если запрос списка - сразу запрашиваем + форматируем отображение
        if (inputCommand[1].equals(Command.LIST_TASK.name())) {
            log.debug("LIST_TASK command. Start handle");
            List<Task> tasks = taskService.findAllByUser(new User(inputCommand[2]));
            log.debug("Tasks by user were collected");

            return ResponseFormatter.createResponse(tasks);
        }

        // для остального -создание, закрытие, открытие, удаление
        return ResponseFormatter.createResponse(handleModifyTask(inputCommand));
    }

    private Result handleModifyTask(String[] inputCommand) {
        log.debug("Start handleModifyTask");
        // если создание задачи - проверяем свободное ли название, если да - создаем
        User user = new User(inputCommand[0]);
        String command = inputCommand[1];
        String taskName = inputCommand[2];

        if (command.equals(Command.CREATE_TASK.name())) {
            log.debug("Chosen command: create task");
            return taskService.createTask(taskName, user);
        }

        if (command.equals(Command.REOPEN_TASK.name())) {
            return taskService.openTask(taskName, user);
        }

        if (command.equals(Command.DELETE_TASK.name())) {
            return taskService.deleteTask(taskName, user);
        }

        if (command.equals(Command.CLOSE_TASK.name())) {
            return taskService.closeTask(taskName, user);
        }

        return Result.ERROR;
    }
}
