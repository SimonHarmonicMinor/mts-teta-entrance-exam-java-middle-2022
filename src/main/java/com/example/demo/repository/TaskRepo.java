package com.example.demo.repository;

import com.example.demo.model.Task;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Репозиторий задач
 */
public class TaskRepo {

    //Хранилище задач
    private final Map<String, Task> taskMap = new HashMap<>();

    //Счетчик ID-шников для задач.
    private long id = 0;

    /**
     * Получить задачу по ее названию
     *
     * @param taskName - название задачи
     * @return возвращает задачу, если она найдена или null, если не найдена.
     */
    public Task findTaskByName(String taskName) {

        return taskMap.get(taskName);

    }

    /**
     * Получить список всех задач пользователя
     * @param user - пользователь
     * @return возвращает список всех задач пользователя
     */
    public List<Task> findTaskByUser(String user) {

        return taskMap
                .values()
                .stream()
                .filter(t -> t.getUser().equals(user) && !t.getStatus().equals(Task.DELETED))
                .collect(Collectors.toList());

    }

    /**
     * Добавить новую задачу
     * @param taskName - имя задачи
     * @param user - пользователь от которого добавляется задача
     * @throws Exception - если задача с таким именем уже существует
     */
    public void addTask(String taskName, String user) throws Exception {

        if (isTaskExist(taskName)) {
            throw new Exception("Task already exist");
        }

        taskMap.put(taskName, new Task(nextId(), taskName, user, Task.CREATED, LocalDateTime.now()));

    }

    /**
     * Изменить статус задачи
     * @param taskName - имя задачи
     * @param status - новый статус задачи
     * @throws Exception - если задача с таким именем не найдена
     */
    public void changeTaskStatus(String taskName, String status) throws Exception {

        Task task = findTaskByName(taskName);
        if (task == null) {
            throw new Exception("Task not found");
        }

        task.setStatus(status);
    }

    /**
     * Удаляет все задачи
     */
    public void deleteAllTasks() {
        taskMap.clear();
    }

    /**
     * Проверяет, существует ли задача с таким именем не в статусе "DELETED"
     * @param taskName - имя задачи
     * @return - true если задача существует
     *           false если задача не существует
     */
    private boolean isTaskExist(String taskName) {

        Task task = findTaskByName(taskName);

        return !(task == null || task.getStatus().equals(Task.DELETED));
    }

    /**
     * Выделяет следующий ID для новой задачи
     * @return - возвращает новый ID
     */
    private long nextId() {
        return ++id;
    }

}
