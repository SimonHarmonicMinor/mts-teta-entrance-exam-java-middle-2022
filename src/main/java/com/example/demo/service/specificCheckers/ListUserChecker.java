package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.Error;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RequestChecker;

import static java.util.Objects.isNull;

/**
 * Проверка на наличие пользователя в списке
 * Не нужна!!!
 * При отсутствии пользователя возвращаем пустой лист
 */
public class ListUserChecker implements RequestChecker {

    UserRepository userRepository;

    @Override
    public void check(Request request) {
        if (request.getCommand().equals(Command.LIST_TASK)) {
            if (isNull(userRepository.getUserByName(request.getUserName()))) {
                throw new Error("Нет пользователя с таким именем");
            }
        }
    }
}
