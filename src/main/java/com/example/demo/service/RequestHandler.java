package com.example.demo.service;

import com.example.demo.exception.AccessException;
import com.example.demo.exception.DemoException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for handling a request and generating a response
 */
public class RequestHandler {

    private final TaskService taskService;

    public RequestHandler(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Handling a request and generating a response
     *
     * @param requestAsString Request
     * @return Response
     */
    public String handle(String requestAsString) {
        try {
            // Request Validation
            Request request = validate(requestAsString);
            // Checking access for performing operation
            authorization(request);
            // Taking action on a task
            return takeActionWithTask(request);
        } catch (ValidationException e) {
            return ResponseResult.WRONG_FORMAT.name();
        } catch (AccessException e) {
            return ResponseResult.ACCESS_DENIED.name();
        } catch (Exception e) {
            return ResponseResult.ERROR.name();
        }
    }

    /**
     * Validating request format
     *
     * @param requestAsString Request as string
     * @return Parsed request
     */
    private Request validate(String requestAsString) {
        String[] requestAsArray = requestAsString.split(" ");
        // Checking the number of arguments in a request
        if (requestAsArray.length != 3) {
            throw new ValidationException("Invalid number of request arguments: request = " + requestAsString);
        }

        // Command validation
        Command command;
        try {
            command = Command.valueOf(requestAsArray[1]);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid command in request: request = " + requestAsString);
        }

        return new Request(requestAsArray[0], command, requestAsArray[2]);
    }

    /**
     * Checking access for performing operation
     *
     * @param request Request
     */
    private void authorization(Request request) {
        // Checking access for performing operation CREATE_TASK and LIST_TASK is not needed
        if (request.getCommand() == Command.CREATE_TASK || request.getCommand() == Command.LIST_TASK) {
            return;
        }

        // Getting a task/getting an exception that the task does not found
        Task task = taskService.getTaskByName(request.getArgument());
        // Checking that the user can only close, delete and reopen his tasks
        if (!task.getUserName().equals(request.getUserName())) {
            throw new AccessException("The user can close, delete and reopen only his tasks");
        }
    }

    /**
     * Taking action on a task
     *
     * @param request Request
     * @return Response
     */
    private String takeActionWithTask(Request request) {
        if (request.getCommand() == Command.LIST_TASK) {
            // Getting a list of user actual tasks
            List<String> taskNames = taskService.getActualTasksByUserName(request.getArgument())
                    .stream()
                    .map(Task::getName)
                    .collect(Collectors.toList());
            return ResponseResult.TASKS.name() + " " + taskNames;
        }

        if (request.getCommand() == Command.CREATE_TASK) {
            // Getting a task/getting an exception that the task name must be unique
            taskService.createTask(request.getUserName(), request.getArgument());
            return ResponseResult.CREATED.name();
        }

        // Changing task status
        TaskStatus taskStatus = taskService.changeTaskStatus(request.getArgument(), request.getCommand());

        return toResponseResult(taskStatus).name();
    }

    /**
     * Converting TaskStatus to ResponseResult
     *
     * @param taskStatus Task status
     * @return Response result
     */
    private ResponseResult toResponseResult(TaskStatus taskStatus) {
        switch (taskStatus) {
            case CREATED:
                return ResponseResult.REOPENED;
            case CLOSED:
                return ResponseResult.CLOSED;
            case DELETED:
                return ResponseResult.DELETED;
            default:
                throw new DemoException("Unknown task status " + taskStatus);
        }
    }
}
