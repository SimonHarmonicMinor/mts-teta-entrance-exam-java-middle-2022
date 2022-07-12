package com.example.demo.model.enums;

public enum ServiceResult {
    CREATED, //— задача успешно создана
    DELETED, //— задача успешно удалена
    CLOSED,// — задача успешно закрыта
    REOPENED,// — задача успешно открыта заново
    TASKS,// [MyTask1, MyTask2] — список задач пользователя. Если задач нет, список пустой ([]). Задачи перечислены в порядке их создания.
    WRONG_FORMAT,// — Неверный формат запроса
    ACCESS_DENIED,// — Нет прав на совершение операции
    ERROR
}
