package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.service.RequestChecker;

/**
 * Проверка имени юзера на написание в upper case
 */

public class UserNameChecker implements RequestChecker {

    @Override
    public void check(Request request) {
        if (!request.getUserName().matches("[A-Z]")) {
            throw new FormatException("Неправильный формат написания имени");
        }
    }

}