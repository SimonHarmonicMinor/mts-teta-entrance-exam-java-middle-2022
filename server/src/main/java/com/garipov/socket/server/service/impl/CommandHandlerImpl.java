package com.garipov.socket.server.service.impl;

import com.garipov.socket.server.mapper.UserRequestMapper;
import com.garipov.socket.server.model.ResultStatus;
import com.garipov.socket.server.dto.ServerResponse;
import com.garipov.socket.server.dto.UserRequest;
import com.garipov.socket.server.service.CommandHandler;
import com.garipov.socket.server.service.CommandValidator;
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
