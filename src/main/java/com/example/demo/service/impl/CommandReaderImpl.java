package com.example.demo.service.impl;

import com.example.demo.exception.ServiceException;
import com.example.demo.model.Command;
import com.example.demo.model.CommandRequest;
import com.example.demo.service.CommandReader;

public class CommandReaderImpl implements CommandReader {
    @Override
    public CommandRequest readFromString(String input) {
        if (input == null || input.isBlank()) {
            throw new ServiceException(ServiceException.ErrorType.WRONG_FORMAT);
        }
        final ReadContext context = new ReadContext();
        String userName = null;
        String commandName = null;
        String commandArg = null;
        for (String word : input.split(" ")) {
            switch (context.getExpectedWordType()) {
                case USER:
                    userName = word;
                    context.setExpectedWordType(ReadContext.WordType.COMMAND_TYPE);
                    break;
                case COMMAND_TYPE:
                    commandName = word;
                    context.setExpectedWordType(ReadContext.WordType.ARGUMENT);
                    break;
                case ARGUMENT:
                    commandArg = word;
                    context.setExpectedWordType(ReadContext.WordType.NONE);
                    break;
                case NONE:
                    throw new ServiceException(ServiceException.ErrorType.WRONG_FORMAT);
            }
        }
        if (context.getExpectedWordType() != ReadContext.WordType.NONE) {
            throw new ServiceException(ServiceException.ErrorType.WRONG_FORMAT);
        }
        return new CommandRequest(userName, commandName, commandArg);
    }

    private static class ReadContext {
        private WordType expectedWordType = WordType.USER;

        void setExpectedWordType(WordType type) {
            expectedWordType = type;
        }

        public WordType getExpectedWordType() {
            return expectedWordType;
        }

        enum WordType {
            USER,
            COMMAND_TYPE,
            ARGUMENT,
            NONE
        }
    }
}
