package com.example.demo.entity;

import com.example.demo.entity.enums.Command;

/**
 * Описание класса запроса
 */
public class Request {
    /**
     * Имя пользователя от которого пришел запрос
     */
    private final String user;
    /**
     * Команда
     */
    private final Command command;

    /**
     * Аргумент
     */
    private final String arg;

    public Request(Builder builder){
        this.user = builder.user;
        this.command = builder.command;
        this.arg = builder.arg;
    }

    /**
     * Построитель запросов
     */
    public static class Builder{
        String user;
        Command command;
        String arg;

        public Builder user(String userName){
            this.user = userName;
            return this;
        }

        public Builder command(String command) throws IllegalArgumentException{
            this.command = Command.valueOf(command);
            return this;
        }

        public Builder command(Command command){
            this.command = command;
            return this;
        }

        public Builder arg(String arg){
            this.arg = arg;
            return this;
        }

        public Request build() throws IllegalArgumentException{
            if (validation())
                return new Request(this);
            else
                throw new IllegalArgumentException("WRONG_FORMAT");
        }

        private boolean validation(){
            return (this.command != null && this.user != null && this.arg != null);
        }
    }

    public String getUser(){
        return this.user;
    }

    public Command getCommand(){
        return this.command;
    }

    public String getArg(){
        return this.arg;
    }

}
