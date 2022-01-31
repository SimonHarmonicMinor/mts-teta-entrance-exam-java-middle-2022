package com.example.demo.utils;

import com.example.demo.enums.ServerCommand;
import com.example.demo.exceptions.WrongFormatException;
import com.example.demo.service.dto.CommandProcessingServiceDto;

/**
 * Утильные методы для работы с пользовательским вводом
 */
public class UserInputUtil {

    private UserInputUtil() {}

    /**
     * Ищем в пользовательском вводе команду сервера и валидируем правильность ввода
     *
     * @param userInput пользовательский ввод
     * @return команда сервера
     */
    public static CommandProcessingServiceDto searchAndValidateServerCommandFromUserInput(String userInput) {
        int numberOfCoincidences = 0;
        ServerCommand singleServerCommand = null;
        for (ServerCommand command : ServerCommand.values()) {
            if (userInput.contains(command.name())) {
                numberOfCoincidences++;
                singleServerCommand = command;
            }
        }
        if (numberOfCoincidences == 1) {
            validateCommandLine(userInput, singleServerCommand);
            return new CommandProcessingServiceDto(singleServerCommand, splitUserInput(userInput));
        } else {
            throw new WrongFormatException();
        }
    }

    /**
     * Валидация пользовательского ввода на соответствие формату запроса: “USER COMMAND ARG”
     *
     * @param userInput пользовательский ввод
     * @param command команда сервера
     */
    private static void validateCommandLine(String userInput, ServerCommand command) {
        String[] commandLine = splitUserInput(userInput);
        if (commandLine.length != 3 || !commandLine[1].equals(command.name())) {
            throw new WrongFormatException();
        }
    }

    private static String[] splitUserInput(String userInput) {
        return userInput.split(" ");
    }
}
