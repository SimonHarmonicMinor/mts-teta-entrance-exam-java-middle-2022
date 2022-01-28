package com.costa.socket.server.mapper;

import com.costa.socket.server.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UserRequestMapper {
    private static final Logger LOG = LoggerFactory.getLogger(UserRequestMapper.class);

    private UserRequestMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Optional<UserRequest> toUserRequest(String command){
        try {
            String[] commandSplit = command.split(" ");
            if (commandSplit.length != 3)
                throw new IllegalArgumentException("The size of the command line does not satisfy the conditions");

            User user = new User(commandSplit[0]);
            AvailableCommand availableCommand = AvailableCommand.valueOf(commandSplit[1]);
            String arg = commandSplit[2];

            UserRequest userRequest = new UserRequest(user, availableCommand, arg);
            return Optional.of(userRequest);
        } catch (IllegalArgumentException e) {
            LOG.error("UserRequest mapping error. Command = {}", command, e);
        }

      return Optional.empty();
    }
}
