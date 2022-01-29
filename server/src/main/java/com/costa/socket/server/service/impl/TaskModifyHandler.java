package com.costa.socket.server.service.impl;

import com.costa.socket.server.dto.ServerResponse;
import com.costa.socket.server.mapper.TaskMapper;
import com.costa.socket.server.model.ResultStatus;
import com.costa.socket.server.dto.UserRequest;
import com.costa.socket.server.service.Validator;
import com.costa.socket.server.service.TaskHandler;
import com.costa.socket.server.daoservice.TaskService;

/**
 * Data modifying task handler
 */
public class TaskModifyHandler implements TaskHandler {
    private final Validator validator;
    private final TaskService taskService;

    public TaskModifyHandler(Validator validator, TaskService taskService) {
        this.validator = validator;
        this.taskService = taskService;
    }

    @Override
    public ServerResponse<ResultStatus> handle(UserRequest request) {
        if (!validator.validateAccess(request))
            return ServerResponse.create(ResultStatus.ACCESS_DENIED);

        if(!taskService.save(TaskMapper.toTask(request)))
            return ServerResponse.error();

        return ServerResponse.create(request.getCommand().getCommandModel().getResultStatus());
    }
}
