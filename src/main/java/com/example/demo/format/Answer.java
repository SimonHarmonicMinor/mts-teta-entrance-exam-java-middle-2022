package com.example.demo.format;

public enum Answer {
    /**
     * задача успешно создана
     */
    CREATED,
    /**
     * задача успешно удалена
     */
    DELETED,
    /**
     * задача успешно закрыта
     */
    CLOSED,
    /**
     * задача успешно открыта заново
     */
    REOPENED,
    /**
     * список задач пользователя
     */
    TASKS,
    /**
     * Неверный формат запроса
     */
    WRONG_FORMAT,
    /**
     * Нет прав на совершение операции
     */
    ACCESS_DENIED,
    /**
     * Любая другая ошибка
     */
    ERROR
}
