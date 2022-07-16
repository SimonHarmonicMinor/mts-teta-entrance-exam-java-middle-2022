package com.example.demo.data;

import com.example.demo.model.Task;
import com.example.demo.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class TaskTrackerData {

    private final HashMap<String, User> users = new HashMap<>();

    private final HashSet<String> activeTaskNames = new HashSet<>();

    /**
     * Метод получения пользователя.
     *
     * @param name имя пользователя
     * @return объект пользователя, если он был, иначе создаётся и возвращается новый пользователь с именем @name
     */
    public User getUser(String name) {
        User user = users.get(name);
        if (Objects.isNull(user)) {
            user = new User(name);
            users.put(name, user);
        }
        return user;
    }

    public boolean checkAccess(String userName, String taskName) {
        User user = users.get(userName);
        if (Objects.isNull(user)) {
            return false;
        }
        return user.hasTask(taskName);
    }

    /**
     * Удаляет все данные тасктрекера
     */
    public void deleteAll() {
        users.clear();
        activeTaskNames.clear();
    }

    /**
     * Находится ли задача с таким именем в состоянии CREATED или CLOSED.
     *
     * @param name имя задачи
     * @return true если существует задача с таким именем и её состояние CREATED или CLOSED, иначе false
     */
    public boolean isTaskActive(String name) {
        return activeTaskNames.contains(name);
    }

    /**
     * Добавление задачи пользователю. Создаётся новая задача и закрепляется за указанным пользователем
     *
     * @param user     пользователь
     * @param taskName имя задачи
     * @return Если задача была успешно создана и закреплена за пользователем, то возвращает её
     */
    public Task userAddTask(User user, String taskName) {
        Task task = new Task(taskName);
        user.addTask(task);
        activeTaskNames.add(taskName);
        return task;
    }

    /**
     * Закрывает открытую задачу
     *
     * @param user     пользователь отправляющий запрос
     * @param taskName имя задачи
     */
    public boolean closeTask(User user, String taskName) {
        return user.closeTask(taskName);
    }

    public boolean reopenTask(User user, String taskName) {
        return user.reopenTask(taskName);
    }

    public boolean deleteTask(String userName, String taskName) {
        User user = users.get(userName);
        if (user.deleteTask(taskName)) {
            activeTaskNames.remove(taskName);
            return true;
        }
        return false;
    }

    public List<Task> getUserTasks(String userName) {
        User user = users.get(userName);
        return user.getTasks();
    }
}
