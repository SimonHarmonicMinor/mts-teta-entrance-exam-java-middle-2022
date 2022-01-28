package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RequestChecker;

import static java.util.Objects.isNull;

/**
 * Проверка на прао исполнение запроса у пользователя
 */
public class RightChecker implements RequestChecker {

    TaskRepository taskRepository;
    UserRepository userRepository;

    @Override
    public void check(Request request) {
        Command currentCommand = request.getCommand();
        if (currentCommand.equals(Command.CLOSE_TASK) || currentCommand.equals(Command.DELETE_TASK) || currentCommand.equals(Command.REOPEN_TASK)) {
            if (isNull(taskRepository.getTasksByUser(userRepository.getUserByName(request.getUserName())))) {
                throw new RuntimeException("Нет прав на совершение действия");
            }
        }
    }
}
