package com.costa.socket.server.service.impl;

import com.costa.socket.server.daoservice.TaskService;
import com.costa.socket.server.model.CommandType;
import com.costa.socket.server.service.Validator;
import com.costa.socket.server.service.TaskHandler;

/**
 * Factory to receive a task handler relative to the type {@link CommandType}
 */
public class TaskHandlerFactory {
    private final Validator validator;
    private final TaskService taskService;

    public TaskHandlerFactory(Validator validator, TaskService taskService) {
        this.validator = validator;
        this.taskService = taskService;
    }

    public TaskHandler getTaskHandler(CommandType type) {
        TaskHandler handler;

        switch (type) {
            case MODIFY_TASK:
                handler = new TaskModifyHandler(validator, taskService);
                break;
            case READ_TASK:
                handler = new TaskReadHandler(taskService);
                break;
            default:
                throw new IllegalArgumentException("Cannot find handler for a type:" + type);
        }

        return handler;
    }
}
