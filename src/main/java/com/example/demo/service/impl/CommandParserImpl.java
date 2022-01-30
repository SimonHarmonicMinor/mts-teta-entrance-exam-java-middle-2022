package com.example.demo.service.impl;

import com.example.demo.exception.ServiceException;
import com.example.demo.model.CommandRequest;
import com.example.demo.service.CommandParser;

public class CommandParserImpl implements CommandParser {
    @Override
    public CommandRequest parseFromString(String input) {
        if (input == null || input.isBlank()) {
            throw new ServiceException(ServiceException.ErrorType.WRONG_FORMAT);
        }
        final String[] words = input.split(" ");
        if (words.length != 3) {
            throw new ServiceException(ServiceException.ErrorType.WRONG_FORMAT);
        }
        final String userName = words[0];
        final String commandName = words[1];
        final String commandArg = words[2];
        return new CommandRequest(userName, commandName, commandArg);
    }
}
