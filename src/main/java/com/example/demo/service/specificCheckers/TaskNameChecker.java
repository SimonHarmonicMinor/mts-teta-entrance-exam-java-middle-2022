package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.service.RequestChecker;

public class TaskNameChecker implements RequestChecker {

    // дополнительный параметр - имя задачи не уникально
    // подумать как задачи хранить
    @Override
    public void check(Request request) {
        if (listOfTask.get(request.getAdditionalParam())) {
            throw new FormatException("Имя задачи не уникально");
        }
    }
}
