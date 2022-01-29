package com.costa.socket.server.service.impl;

import com.costa.socket.server.dto.ServerResponse;
import com.costa.socket.server.dto.UserRequest;
import com.costa.socket.server.model.*;
import com.costa.socket.server.service.TaskHandler;
import com.costa.socket.server.daoservice.TaskService;

import java.util.Arrays;

/**
 * Data reading task handler
 */
public class TaskReadHandler implements TaskHandler {
    private final TaskService taskService;

    public TaskReadHandler(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public ServerResponse<ResultStatus> handle(UserRequest request) {
        if (AvailableCommand.LIST_TASK == request.getCommand()) {
            return ServerResponse.create(ResultStatus.TASKS,
                    Arrays.toString(taskService.findAllByUserName(request.getArg()).stream()
                            .filter(x -> !x.getState().equals(TaskState.DELETED))
                            .map(Task::getDescription).toArray()));
        }

        return ServerResponse.error();
    }
}
