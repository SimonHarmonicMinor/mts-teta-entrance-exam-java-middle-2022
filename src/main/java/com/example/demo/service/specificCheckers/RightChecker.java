package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.RightException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RequestChecker;

import java.util.List;

/**
 * Проверка на право исполнение запроса у пользователя
 */
public class RightChecker implements RequestChecker {

    UserRepository userRepository;

    @Override
    public void check(Request request) {
        Command currentCommand = request.getCommand();
        List<String> taskList = userRepository.getUserByName(request.getUserName()).getTaskName();
        if (currentCommand.equals(Command.CLOSE_TASK) || currentCommand.equals(Command.DELETE_TASK) || currentCommand.equals(Command.REOPEN_TASK)) {
            for (String taskName : taskList) {
                if (!taskName.equals(request.getAdditionalParam())) {
                    throw new RightException("Нет прав на совершение действия");
                }
            }
        }
    }
}
