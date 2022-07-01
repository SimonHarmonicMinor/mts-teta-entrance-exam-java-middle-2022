package com.example.demo.model;

/**
 * Описывает структуру запроса
 */
public class Command {

    /**
     * Пользователь от которого выполняется команда
     */
    private String user;

    /**
     * Действие (команда)
     */
    private String cmdName;

    /**
     * Аргумент команды
     */
    private String arg;

    /**
     * Соответствует ли строка запроса требуемому формату
     */
    private boolean isValid;

    public Command(String user, String cmdName, String arg, boolean isValid) {
        this.user = user;
        this.cmdName = cmdName;
        this.arg = arg;
        this.isValid = isValid;
    }

    public String getUser() {
        return user;
    }

    public String getCmdName() {
        return cmdName;
    }

    public String getArg() {
        return arg;
    }

    public boolean isValid() {
        return isValid;
    }
}
