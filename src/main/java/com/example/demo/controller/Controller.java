package com.example.demo.controller;

import com.example.demo.Server;
import com.example.demo.model.Request;
import com.example.demo.model.Response;
import com.example.demo.model.types.CommandType;
import com.example.demo.model.types.ResponseType;
import com.example.demo.service.TaskService;
import com.example.demo.util.RequestParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Controller {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private final TaskService taskService;

    public Controller(TaskService taskService) {
        this.taskService = taskService;
    }

    public String defineResponseByRequest(String input) {
        Request request;
        try {
            request = RequestParser.parse(input);
        } catch (Exception ex) {
            LOG.error("Неверный формат запроса - " + ex);
            return new Response(ResponseType.WRONG_FORMAT).toString();
        }
        switch (CommandType.getCommandType(request.getCommand())) {
            case CREATE_TASK:
                return taskService.createTask(request);
            case DELETE_TASK:
                return taskService.deleteTask(request);
            case CLOSE_TASK:
                return taskService.closeTask(request);
            case REOPEN_TASK:
                return taskService.reopenTask(request);
            case LIST_TASK:
                return taskService.listTask(request);
            default:
                return new Response(ResponseType.WRONG_FORMAT).toString();
        }
    }
}
