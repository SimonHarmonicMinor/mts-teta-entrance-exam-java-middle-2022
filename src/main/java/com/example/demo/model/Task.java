package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Описывает структуру задачи
 */
public class Task {

    /**
     * Идентификатор задачи
     */
    private long id;

    /**
     * Имя задачи
     */
    private String name;

    /**
     * Пользователь, создавший задачу
     */
    private String user;

    /**
     * Статус задачи. Допустимые статусы: CREATED CLOSED DELETED
     */
    private String status;

    /**
     * Дата и Время создания задачи
     */
    private LocalDateTime crateDate;

    public Task(long id, String name, String user, String status, LocalDateTime crateDate) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.status = status;
        this.crateDate = crateDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCrateDate() {
        return crateDate;
    }

    public void setCrateDate(LocalDateTime crateDate) {
        this.crateDate = crateDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
