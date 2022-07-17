package com.example.demo.model;

/**
 * Описывает структуру запроса
 */
public class Command {

    /**
     * Команды
     */
    public static final String CREATE_TASK = "CREATE_TASK";
    public static final String CLOSE_TASK = "CLOSE_TASK";
    public static final String DELETE_TASK = "DELETE_TASK";
    public static final String REOPEN_TASK = "REOPEN_TASK";
    public static final String LIST_TASK = "LIST_TASK";
    public static final String __DELETE_ALL = "__DELETE_ALL";

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
