package com.example.demo.entity;

public abstract class AbstractTask {
    /**
     * Имя задание. Зафиналено т.к. нет сеттера
     */
    final String name;

    /**
     * Пользователь создавщий задание. Зафиналено т.к. нет сеттера
     */
    final String userName;

    public AbstractTask(String name, String userName){
        this.name = name;
        this.userName = userName;
    }

    public String getName(){
        return this.name;
    }

    public String getUserName(){
        return this.userName;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
