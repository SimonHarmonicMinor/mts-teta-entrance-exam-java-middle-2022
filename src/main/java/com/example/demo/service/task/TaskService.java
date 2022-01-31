package com.example.demo.service.task;

import com.example.demo.service.dto.CommandParams;

/**
 * Сервис по работе с задачами
 */
public interface TaskService {

    /**
     * Создание задачи
     *
     * @param commandParams команда для задачи
     * @return "CREATED"
     */
    String createTask(CommandParams commandParams) throws Exception;

    /**
     * Удаление задачи
     *
     * @param commandParams команда для задачи
     * @return "DELETED"
     */
    String deleteTask(CommandParams commandParams) throws Exception;

    /**
     * Закрытие задачи
     *
     * @param commandParams команда для задачи
     * @return "CLOSED"
     */
    String closeTask(CommandParams commandParams) throws Exception;

    /**
     * Переоткрыть задачу
     *
     * @param commandParams команда для задачи
     * @return "REOPENED"
     */
    String reOpenTask(CommandParams commandParams) throws Exception;

    /**
     * Список задач
     *
     * @param commandParams команда для задачи
     * @return "TASKS []"
     */
    String listTask(CommandParams commandParams);
}