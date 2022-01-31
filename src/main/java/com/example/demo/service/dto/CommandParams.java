package com.example.demo.service.dto;

import java.util.Objects;

/**
 * Параметры команды переданные пользователем
 */
public class CommandParams {
    /**
     * Имя пользователя, который осуществляет действия
     */
    private String user;
    /**
     * Команда для задачи
     */
    private String commandType;
    /**
     * Имя задачи
     */
    private String taskName;
    /**
     * Аргумент запроса
     */
    private String arg;

    public CommandParams(String user, String commandType, String taskName) {
        this.user = user;
        this.commandType = commandType;
        this.taskName = taskName;
    }

    public CommandParams(String user, String commandType, String taskName, String arg) {
        this.user = user;
        this.commandType = commandType;
        this.taskName = taskName;
        this.arg = arg;
    }

    public String getUser() {
        return user;
    }

    public String getCommandType() {
        return commandType;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getArg() {
        return arg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandParams that = (CommandParams) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(commandType, that.commandType) &&
                Objects.equals(taskName, that.taskName) &&
                Objects.equals(arg, that.arg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, commandType, taskName, arg);
    }
}