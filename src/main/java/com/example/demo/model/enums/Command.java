package com.example.demo.model.enums;

public enum Command {
    CREATE_TASK,// MyTask — создать задачу с названием MyTask
    CLOSE_TASK,// MyTask — закрыть задачу MyTask
    DELETE_TASK,// MyTask — удалить задачу MyTask
    REOPEN_TASK,// MyTask — заново открыть задачу MyTask
    LIST_TASK, //USER— Получить список задач пользователя (в статусе CREATED и CLOSED)
    __DELETE_ALL
}
