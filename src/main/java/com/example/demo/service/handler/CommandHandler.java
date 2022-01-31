package com.example.demo.service.handler;

/**
 * Обработчик команд сервера
 */
public interface CommandHandler {

    /**
     * Обработка входящей команды сервера
     * @param splitUserInput разделенный на части пользовательский ввод
     * @return ответ сервера
     */
    String processCommand(String[] splitUserInput);
}
