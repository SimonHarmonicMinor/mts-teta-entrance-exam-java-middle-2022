package com.example.demo.service.dto;

import com.example.demo.enums.ServerCommand;

/**
 * ДТО для передачи информации о введенной команде со списком аргументов
 */
public class CommandProcessingServiceDto {

    /**
     * Команда сервера
     */
    private final ServerCommand serverCommand;

    /**
     * Разбитый на части запрос пользователя
     */
    private final String[] splitInput;

    public CommandProcessingServiceDto(ServerCommand serverCommand, String[] splitInput) {
        this.serverCommand = serverCommand;
        this.splitInput = splitInput;
    }

    public ServerCommand getServerCommand() {
        return serverCommand;
    }

    public String[] getSplitInput() {
        return splitInput;
    }
}
