package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.service.RequestChecker;

import static java.util.Objects.isNull;

/**
 * Проверка запроса на заполнение обязательных полей запроса
 */

public class RequiredFieldsChecker implements RequestChecker {

    @Override
    public void check(Request request) {
        if (isNull(request) || isNull(request.getCommand()) || isNull(request.getUserName()) || isNull(request.getAdditionalParam())) {
            throw new FormatException("Необходимый параметр null");
        }
    }
}
