package com.example.demo;

import com.example.demo.commands.*;

import java.util.HashMap;
import java.util.Map;
//*********************************************************************************
// Created by Aleksej Blagodatskih, avblago1@mts.ru, alek_zrg@mail.ru vk: https://vk.com/id12358512
// Here I tried to use pattern "Command".
//*********************************************************************************
public class CommandExecutor {
    private static final Map<Commands, Command> allCommandsMap = new HashMap<>();
    static {
        allCommandsMap.put(Commands.CREATE_TASK, new CreateTask());
        allCommandsMap.put(Commands.CLOSE_TASK, new CloseTask());
        allCommandsMap.put(Commands.DELETE_TASK, new DeleteTask());
        allCommandsMap.put(Commands.REOPEN_TASK, new ReopenTask());
        allCommandsMap.put(Commands.LIST_TASK, new ListTask());
        allCommandsMap.put(Commands.__DELETE_ALL, new DeleteAll());
    }

    public static String Execute(String sentCommand) {
        String[] parsedCommand = new String[0];
        if (sentCommand.equals("__DELETE_ALL")) return allCommandsMap.get(Commands.__DELETE_ALL).execute(parsedCommand);
        parsedCommand = parseCommand(sentCommand);
        if (parsedCommand == null) return "WRONG_FORMAT";
        try {
            return allCommandsMap.get(Commands.valueOf(parsedCommand[1])).execute(parsedCommand);
        } catch (IllegalArgumentException e) {
            return "WRONG_FORMAT";
        }
    }

    public static String[] parseCommand(String string) { //разбиваем строку пробелами и все проверки делаем тут, при ошибке вернёт null
        String[] parsedString = string.split(" ");
        if (parsedString.length == 3) {
            try {
                if (allCommandsMap.containsKey(Commands.valueOf(parsedString[1]))) return parsedString;
                else return null;
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        else return null;
    }
}
