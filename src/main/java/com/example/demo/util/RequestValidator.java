package com.example.demo.util;

import com.example.demo.model.Command;

// Базовые проферки (формата запроса)
public class RequestValidator {
    public static boolean validateRequest(String[] inputCommand) {
        return !(validateRequestLength(inputCommand) & validateCommand(inputCommand[1]));
    }


    private static boolean validateRequestLength(String[] inputCommand) {
        return inputCommand.length == 3;
    }

    private static boolean validateCommand(String stringCommand) {
        try {
            Command command = Command.valueOf(stringCommand);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
