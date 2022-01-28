package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.service.RequestChecker;

import java.util.regex.Pattern;

public class UserNameChecker implements RequestChecker {

    // имя юзера написано с маленькой буквы(регистрозависимость) - команды и имена пользователей регистрозависимые
    @Override
    public void check(Request request) {
        if (!request.getUserName().matches("[A-Z]")) {
            throw new FormatException("Неправильный формат написания имени");
        }
    }

}