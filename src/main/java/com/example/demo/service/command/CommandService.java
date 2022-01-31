package com.example.demo.service.command;

/**
 * Сервис по выполнению команды
 */
public interface CommandService {

    /**
     * Запуск команды
     *
     * @param command команда
     * @return результат выполнения команды
     */
    String execute(String command);
}