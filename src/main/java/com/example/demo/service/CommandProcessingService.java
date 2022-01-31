package com.example.demo.service;

import com.example.demo.service.dto.CommandProcessingServiceDto;

import static com.example.demo.service.CommandHandlerLocator.getHandler;
import static com.example.demo.utils.UserInputUtil.searchAndValidateServerCommandFromUserInput;

public class CommandProcessingService {

    private CommandProcessingService() {}


    /**
     * Обрабатываем пользовательский ввод и возвращаем ответ сервера
     *
     * @param userInput пользовательский ввод
     * @return ответ сервера
     */
    public static String getResponse(String userInput) {
        CommandProcessingServiceDto dto = searchAndValidateServerCommandFromUserInput(userInput);
        return getHandler(dto.getServerCommand()).processCommand(dto.getSplitInput());
    }

}
