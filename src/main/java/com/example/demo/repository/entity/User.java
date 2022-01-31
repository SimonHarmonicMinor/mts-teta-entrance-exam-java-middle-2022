package com.example.demo.repository.entity;

import java.util.Objects;

/**
 * Пользователь/владелец задачи
 */
public class User {
    /**
     * Имя пользователя
     */
    private final String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}