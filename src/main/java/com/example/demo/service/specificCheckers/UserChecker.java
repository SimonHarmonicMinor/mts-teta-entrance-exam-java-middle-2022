package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.repository.UserRepositoryImpl;
import com.example.demo.service.RequestChecker;

import static java.util.Objects.isNull;

public class UserChecker implements RequestChecker {
    // дополнительный параметр - нет такого имени юзера

    UserRepositoryImpl userRepository = new UserRepositoryImpl();

    @Override
    public void check(Request request) {
        if (isNull(userRepository.getUserByName(request.getAdditionalParam()))) {
            throw new FormatException("Такого пользователя не существует");
        }
    }
}
