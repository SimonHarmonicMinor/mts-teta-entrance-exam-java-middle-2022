package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.service.RequestChecker;

public class UserChecker implements RequestChecker {
    // дополнительный параметр - нет такого имени юзера
    @Override
    public void check(Request request) {
        if (!(listOfUsers.get(request.getAdditionalParam()))) {
            throw new FormatException("Такого пользователя не существует");
        }
    }
}
