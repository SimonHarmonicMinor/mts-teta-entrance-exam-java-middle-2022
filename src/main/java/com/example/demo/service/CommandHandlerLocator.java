package com.example.demo.service;

import com.example.demo.enums.ServerCommand;
import com.example.demo.service.handler.*;

import java.util.Map;

import static com.example.demo.enums.ServerCommand.*;

/**
 * На основе команды в пользовательском вводе возвращает конкретный обработчик этой команды
 */
public class CommandHandlerLocator {

    private CommandHandlerLocator() {}

    /**
     * Карта с командой и ее обработчиком
     */
    private static final Map<ServerCommand, CommandHandler> COMMAND_HANDLER_MAP = Map.of(
            CREATE_TASK, new CreateTaskHandler(),
            CLOSE_TASK, new CloseTaskHandler(),
            DELETE_TASK, new DeleteTaskHandler(),
            REOPEN_TASK, new ReopenTaskHandler(),
            LIST_TASK, new ListTaskHandler()
    );

    /**
     * Метод выбирает обработчик для команды
     *
     * @param command команда
     * @return обработчик команды
     */
    public static CommandHandler getHandler(ServerCommand command) {
        return COMMAND_HANDLER_MAP.get(command);
    }
}
