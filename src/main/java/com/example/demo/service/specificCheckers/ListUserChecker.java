package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.Error;
import com.example.demo.repository.UserRepositoryImpl;
import com.example.demo.service.RequestChecker;

import static java.util.Objects.isNull;

public class ListUserChecker implements RequestChecker {

// проверка на наличие пользователя в списке ????
    // подумать о хранении пользователя

    UserRepositoryImpl userRepository = new UserRepositoryImpl();

    @Override
    public void check(Request request) {
        if (request.getCommand().equals(Command.LIST_TASK)) {
            if (isNull(userRepository.getUserByName(request.getUserName()))) {
                throw new Error("Нет пользователя с таким именем");
            }
        }
    }
}
