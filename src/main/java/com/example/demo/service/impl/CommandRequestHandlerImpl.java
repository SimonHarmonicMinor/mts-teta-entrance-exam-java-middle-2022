package com.example.demo.service.impl;

import com.example.demo.exception.ServiceException;
import com.example.demo.model.Command;
import com.example.demo.model.CommandRequest;
import com.example.demo.model.User;
import com.example.demo.service.CommandRequestHandler;
import com.example.demo.service.UserService;

import java.util.Optional;

public class CommandRequestHandlerImpl implements CommandRequestHandler {
    private final UserService userService = new UserServiceImpl();

    @Override
    public Command handle(CommandRequest commandRequest) {
        final User user = userService.findByName(commandRequest.getUserName())
                .orElseThrow(() -> new ServiceException(ServiceException.ErrorType.ACCESS_DENIED));
        final Command.Type commandType = Command.Type.findByName(commandRequest.getCommandName())
                .orElseThrow(() -> new ServiceException(ServiceException.ErrorType.WRONG_FORMAT));
        final String argument = Optional.ofNullable(commandRequest.getCommandArg())
                .orElseThrow(() -> new ServiceException(ServiceException.ErrorType.WRONG_FORMAT));
        return new Command(user, commandType, argument);
    }
}
