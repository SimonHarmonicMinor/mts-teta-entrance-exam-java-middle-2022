package com.example.demo.request;

/**
 * For response.
 *
 * Create by fkshistero on 30.01.2022.
 */
public enum Response {
    CREATED,// задача успешно создана
    DELETED,// задача успешно удалена
    CLOSED,// задача успешно закрыта
    REOPENED,// задача успешно открыта заново
    TASKS,// [MyTask1, MyTask2]` - список задач пользователя. Если задач нет, список пустой (`[]`)
    WRONG_FORMAT,// Неверный формат запроса
    ACCESS_DENIED,// Нет прав на совершение операции
    ERROR;// Любая другая ошибка
}
