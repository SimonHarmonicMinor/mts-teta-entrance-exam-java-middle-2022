package com.example.demo.service;

import com.example.demo.exceptions.AccessDeniedException;
import com.example.demo.exceptions.UniquenessViolationException;
import com.example.demo.exceptions.WrongFormatException;
import com.example.demo.exceptions.WrongTaskStatusException;
import com.example.demo.model.Command;
import com.example.demo.model.CommandResult;
import com.example.demo.model.Task;
import com.example.demo.model.TaskStatus;
import com.example.demo.repository.Tasks;

import java.util.List;

public class CommandProcessor {

    private String request;
    private String user;
    private Command command;
    private String arg = "";

    public CommandProcessor(String request) {
        this.request = request;
    }

    public String getResponse() {
        //формат запроса
        try {
            checkFormat();
        } catch (Exception e) {
            return CommandResult.WRONG_FORMAT.value();
        }

        //право на совершение операции
        try {
            checkAccess();
        } catch (Exception e) {
            return CommandResult.ACCESS_DENIED.value();
        }

        //все остальные проверки
        try {
            //отсутствие при создании
            checkUniqueness();
            //наличие и статус при изменении
            checkStatus();
        } catch (Exception e) {
            return CommandResult.ERROR.value();
        }

        //выполнение
        switch (command) {
            case CREATE_TASK:
                Tasks.add(new Task(user, arg));
                return CommandResult.CREATED.value();
            case DELETE_TASK:
                Tasks.getByName(arg).setStatus(TaskStatus.DELETED);
                return CommandResult.DELETED.value();
            case CLOSE_TASK:
                Tasks.getByName(arg).setStatus(TaskStatus.CLOSED);
                return CommandResult.CLOSED.value();
            case REOPEN_TASK:
                Tasks.getByName(arg).setStatus(TaskStatus.CREATED);
                return CommandResult.REOPENED.value();
            case LIST_TASK:
                List<Task> userTasks = Tasks.getByOwner(arg);
                return CommandResult.TASKS.value() + " " + userTasks.toString();
            default:
                return CommandResult.ERROR.value();
        }
    }

    private void checkFormat() throws Exception {
        String [] commandLine = request.split(" ");
        user = commandLine[0];
        command = Command.fromValue(commandLine[1]);
        arg = commandLine[2];
        if (commandLine.length > 3) {
            throw new WrongFormatException("Too long command");
        }
    }

    private void checkAccess() throws AccessDeniedException {
        if (command != Command.CREATE_TASK && command != Command.LIST_TASK) {
            String taskOwner = Tasks.getByName(arg).getOwner();
            if (!taskOwner.equals(user)) {
                throw new AccessDeniedException("Requested user is not the owner of the task");
            }
        }
    }

    private void checkUniqueness() {
        Task task = Tasks.getByName(arg);
        if (command == Command.CREATE_TASK && task != null && task.getStatus() != TaskStatus.DELETED) {
            throw new UniquenessViolationException("Requested task name already exists");
        }
    }

    private void checkStatus() {
        Task task = Tasks.getByName(arg);
        if (command == Command.DELETE_TASK && (task == null || task.getStatus() != TaskStatus.CLOSED)) {
            throw new WrongTaskStatusException("Impossible to delete not closed task");
        }
        if (command == Command.CLOSE_TASK && (task == null || task.getStatus() != TaskStatus.CREATED)) {
            throw new WrongTaskStatusException("Impossible to close not active task");
        }
        if (command == Command.REOPEN_TASK && (task == null || task.getStatus() != TaskStatus.CLOSED)) {
            throw new WrongTaskStatusException("Impossible to reopen not closed task");
        }
    }
}
