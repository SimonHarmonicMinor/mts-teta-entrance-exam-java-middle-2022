package com.example.demo.repository;

import com.example.demo.repository.entity.Task;

import java.util.Collection;
import java.util.Optional;

/**
 * Репозиторий задач
 */
public interface TaskRepository {

    /**
     * Создать задачу
     *
     * @param task задача
     * @throws Exception исключение, если задача(!DELETED) с таким названием уже есть
     */
    void createTask(Task task) throws Exception;

    /**
     * Поиск задачи по имени
     *
     * @param name название задачи
     * @return задача
     */
    Optional<Task> findTaskByName(String name);

    /**
     * Обновить задачу
     *
     * @param task обновляемая задача
     * @throws Exception исключение, когда такой задачи нет
     */
    void updateTask(Task task) throws Exception;

    /**
     * Поиск задач по имени пользователя/владельца
     *
     * @param userName имя пользователя
     * @return список задач
     */
    Collection<Task> findTasksByUser(String userName);

    /**
     * Удалить все задачи
     */
    void removeAll();
}