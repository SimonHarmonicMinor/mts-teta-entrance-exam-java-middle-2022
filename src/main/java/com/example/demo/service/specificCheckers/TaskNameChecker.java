package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.repository.TaskRepositoryImpl;
import com.example.demo.service.RequestChecker;

import static java.util.Objects.nonNull;

public class TaskNameChecker implements RequestChecker {

    TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();

    // дополнительный параметр - имя задачи не уникально
    // подумать как задачи хранить
    @Override
    public void check(Request request) {
        if (nonNull(taskRepository.getTaskByName(request.getAdditionalParam()))) {
            throw new FormatException("Имя задачи не уникально");
        }
    }
}
