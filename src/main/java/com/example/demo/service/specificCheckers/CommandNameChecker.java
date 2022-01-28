package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.service.RequestChecker;

import java.util.EnumSet;

/**
 * Проверка запроса на правильность команды
 */

public class CommandNameChecker implements RequestChecker {

    @Override
    public void check(Request request) {
        if (!EnumSet.allOf(Command.class).contains(request.getCommand())) {
            throw new FormatException("Некорректная команда");
        }
    }
}
