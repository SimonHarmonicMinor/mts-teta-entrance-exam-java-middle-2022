package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.service.RequestChecker;

/**
 * Проверка правильного написания имени в дополнительном параметре
 */
public class AdditionalParamChecker implements RequestChecker {

    @Override
    public void check(Request request) {
        if (request.getCommand().equals(Command.LIST_TASK)) {
            if (!request.getAdditionalParam().matches("[A-Z]")) {
                throw new FormatException("Неправильный формат написания имени");
            }
        }
    }
}
