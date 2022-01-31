package com.example.demo.service.dto;

/**
 * Тип команды(действие над задачей)
 */
public enum CommandType {
    CREATE_TASK,
    DELETE_TASK,
    CLOSE_TASK,
    REOPEN_TASK,
    LIST_TASK
}