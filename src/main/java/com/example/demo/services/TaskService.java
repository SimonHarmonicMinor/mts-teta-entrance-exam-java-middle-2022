package com.example.demo.services;

import com.example.demo.entity.AbstractTask;
import com.example.demo.entity.Storable;
import com.example.demo.entity.Task;
import com.example.demo.entity.enums.TaskState;
import com.example.demo.exceptions.StoreException;

import java.util.ArrayList;
import java.util.List;


/**
 * Сервис для работы с тасками
 * Для работы требует хранилище реализующее интерфейс {@link Storable}
 */
public class TaskService {

    private final Storable<AbstractTask> store;

    public TaskService(Storable<AbstractTask> store){
        this.store = store;
    }

    /**
     * Создание таска
     * @param userName имя пользователя
     * @param taskName имя таска
     * @throws StoreException ошибка при сохранении
     */
    public void create(String userName, String taskName) throws StoreException {
        Task task = new Task(taskName, userName);
        store.save(task);
    }

    /**
     * Удаление таска
     * @param task задача
     * @throws Exception ошибка при удалении таска
     */
    public void delete(Task task) throws Exception{
        task.setState(TaskState.DELETED);
        store.delete(task);
    }

    /**
     * Изменение состояния таска
     * @param task задача
     * @param state статус
     * @throws Exception ошибка при изменении статуса таска
     */
    public void changeState(Task task, TaskState state) throws Exception{
        task.setState(state);
        store.save(task);
    }

    /**
     * Получение списка тасков по имени пользователя
     * @param userName пользователь
     * @return Список тасков
     */
    public List getList(String userName){
        return new ArrayList<>(store.get(userName));
    }

    /**
     * Получение таска по имени пользователя и имени таска
     * @param userName пользователь
     * @param taskName имя таска
     * @return найденный таск
     * @throws StoreException ошибка при поиске таска
     */
    public Task getTask(String userName, String taskName) throws StoreException{
        return (Task) store.findByUserAndTask(userName, taskName);
    }
}
