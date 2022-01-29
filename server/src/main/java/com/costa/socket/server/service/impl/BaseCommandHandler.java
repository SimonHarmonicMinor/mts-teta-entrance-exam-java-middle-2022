package com.costa.socket.server.service.impl;

import com.costa.socket.server.dto.ServerResponse;
import com.costa.socket.server.mapper.UserRequestMapper;
import com.costa.socket.server.model.Command;
import com.costa.socket.server.model.ResultStatus;
import com.costa.socket.server.dto.UserRequest;
import com.costa.socket.server.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Basic/initial user command handler
 */
public class BaseCommandHandler implements CommandHandler<ResultStatus> {
    private static final Logger LOG = LoggerFactory.getLogger(BaseCommandHandler.class);
    private final Validator validator;
    private final TaskHandlerFactory handlerFactory;

    public BaseCommandHandler(Validator validator, TaskHandlerFactory handlerFactory) {
        this.validator = validator;
        this.handlerFactory = handlerFactory;
    }

    @Override
    public ServerResponse<ResultStatus> handle(String command) {
        try {
            if (!validator.validateComposition(command))
                return ServerResponse.create(ResultStatus.WRONG_FORMAT);

            UserRequest request = UserRequestMapper.toUserRequest(command)
                    .orElseThrow(() -> new IllegalArgumentException("Error mapping user command"));

            Command userCommand = request.getCommand().getCommandModel();
            TaskHandler taskHandler = handlerFactory.getTaskHandler(userCommand.getType());

            return taskHandler.handle(request);
        } catch (Exception e) {
            LOG.error("Error processing user command {}", command, e);
        }
        return ServerResponse.error();
    }
}
