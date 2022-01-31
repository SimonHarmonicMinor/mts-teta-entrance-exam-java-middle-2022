package com.example.demo.validators;

import com.example.demo.enums.TaskStatus;
import com.example.demo.exceptions.AnyOtherErrorException;

import java.util.*;

import static com.example.demo.enums.TaskStatus.*;
import static java.util.Collections.singletonList;

/**
 * Валидатор смены статуса Задачи
 */
public class ChangeTaskStatusValidator {

    /**
     * Карта допустимых значений статусов с точки зрения текущего статуса
     */
    private Map<TaskStatus, List<TaskStatus>> acceptableTaskStatusMap;

    private static ChangeTaskStatusValidator instance;

    private ChangeTaskStatusValidator() {}

    private ChangeTaskStatusValidator(Map<TaskStatus, List<TaskStatus>> acceptableTaskStatusMap) {
        this.acceptableTaskStatusMap = acceptableTaskStatusMap;
    }

    public static ChangeTaskStatusValidator getInstance() {
        if (instance == null) {
            instance = new ChangeTaskStatusValidator(createAcceptableTaskStatusMap());
        }
        return instance;
    }

    public void validate(TaskStatus currentTaskStatus, TaskStatus newTaskStatus) {
        if (!acceptableTaskStatusMap.get(currentTaskStatus).contains(newTaskStatus)) {
            throw new AnyOtherErrorException("Невозможно сменить статус Задачи: "
                    + currentTaskStatus.name()
                    + " -> "
                    + newTaskStatus.name());
        }
    }

    private static Map<TaskStatus, List<TaskStatus>> createAcceptableTaskStatusMap() {
        Map<TaskStatus, List<TaskStatus>> taskStatusTaskStatusMap = new EnumMap<>(TaskStatus.class);
        taskStatusTaskStatusMap.put(CREATED, singletonList(CLOSED));
        taskStatusTaskStatusMap.put(REOPENED, singletonList(CLOSED));
        taskStatusTaskStatusMap.put(CLOSED, Arrays.asList(DELETED, REOPENED));
        taskStatusTaskStatusMap.put(DELETED, new ArrayList<>());
        return taskStatusTaskStatusMap;
    }
}
