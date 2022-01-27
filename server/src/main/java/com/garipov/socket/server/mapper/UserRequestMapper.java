package com.garipov.socket.server.mapper;

import com.garipov.socket.server.model.TaskCommand;
import com.garipov.socket.server.dto.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UserRequestMapper {
    private static final Logger LOG = LoggerFactory.getLogger(UserRequestMapper.class);

    private UserRequestMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Optional<UserRequest> toUserRequest(String command) {
        try {
            String[] commandSplit = command.split(" ");
            if (commandSplit.length != 3)
                throw new IllegalArgumentException("The size of the command line does not satisfy the conditions");

            return Optional.of(new UserRequest(commandSplit[0],
                    TaskCommand.valueOf(commandSplit[1]),
                    commandSplit[2]));
        } catch (IllegalArgumentException e) {
            LOG.error("Entity mapping error. Command = {}", command, e);
        }

        return Optional.empty();
    }
}
