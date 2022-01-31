package com.example.demo.repository.entity;

import java.util.UUID;

/**
 * Задача
 */
public class Task {
    /**
     * id задачи
     */
    private final String id;
    /**
     * Уникальное название задачи
     */
    private final String name;
    /**
     * Владелец задачи
     */
    private final User user;
    /**
     * Статус задачи
     */
    private TaskStatus status;

    public Task(String name, User user, TaskStatus status) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.user = user;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}