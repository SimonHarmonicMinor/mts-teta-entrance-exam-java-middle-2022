package com.costa.socket.server.service.impl;

import com.costa.socket.server.service.CommandHandler;
import com.costa.socket.server.mapper.UserRequestMapper;
import com.costa.socket.server.model.ResultStatus;
import com.costa.socket.server.dto.ServerResponse;
import com.costa.socket.server.dto.UserRequest;
import com.costa.socket.server.service.CommandValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandlerImpl implements CommandHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CommandHandlerImpl.class);
    private final CommandValidator validator;

    public CommandHandlerImpl(CommandValidator commandValidator) {
        this.validator = commandValidator;
    }

    @Override
    public ServerResponse handle(String command) {
        try {
            if (!validator.validateComposition(command))
                return new ServerResponse(ResultStatus.WRONG_FORMAT);

            UserRequest request = UserRequestMapper.toUserRequest(command)
                    .orElseThrow(() -> new IllegalArgumentException("Error mapping user command"));


        } catch (Exception e) {
            LOG.error("Error processing user command {}", command, e);
            return new ServerResponse(ResultStatus.ERROR);
        }
        return null;
    }
}
