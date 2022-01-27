package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.service.RequestChecker;

import static java.util.Objects.isNull;

public class RequiredFieldsChecker implements RequestChecker {

    // проверка на заполнение обязательных полей
    @Override
    public void check(Request request) {
        if (isNull(request) || isNull(request.getCommand()) || isNull(request.getUser()) || isNull(request.getAdditionalParam())) {
            throw new FormatException("Необходимый параметр null");
        }
    }
}
