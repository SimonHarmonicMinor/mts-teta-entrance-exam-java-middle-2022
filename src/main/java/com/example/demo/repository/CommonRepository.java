package com.example.demo.repository;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.enums.TaskStatus;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Общий репозиторий для хранения данных in-memory
 */
public class CommonRepository {

    private static CommonRepository instance;

    private CommonRepository() {}

    public static CommonRepository getInstance() {
        if (instance == null) {
            instance = new CommonRepository();
        }
        return instance;
    }

    private final Set<User> users = new HashSet<>();

    public Optional<User> getUserByName(String userName) {
        return users.stream().filter(user -> userName.equals(user.getUserName())).findFirst();
    }

    public void saveNewUser(User user) {
        users.add(user);
    }

    public Optional<Task> getTaskByName(String taskName) {
        return users.stream()
                .flatMap(user -> user.getTasks().stream())
                .filter(task -> !TaskStatus.DELETED.equals(task.getTaskStatus()))
                .filter(task -> taskName.equals(task.getTaskName()))
                .findFirst();
    }

    public void updateUserTaskList(User user, Set<Task> tasks) {
        users.remove(user);
        user.setTasks(tasks);
        users.add(user);
    }
}
