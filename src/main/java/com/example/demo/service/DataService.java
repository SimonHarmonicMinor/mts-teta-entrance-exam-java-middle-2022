package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.exceptions.AnyOtherErrorException;
import com.example.demo.repository.CommonRepository;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static com.example.demo.enums.TaskStatus.CREATED;
import static com.example.demo.utils.TaskListFormatter.formatTaskListResponse;

/**
 * Сервис для работы с общим репозиторием (хранилище данных)
 */
public class DataService {

    private static DataService instance;

    private final CommonRepository repository;

    private DataService() {
        this.repository = CommonRepository.getInstance();
    }

    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }


    public String getUserTaskList(String userName) {
        User user = repository.getUserByName(userName).orElseThrow(
                () -> new AnyOtherErrorException("Запрошен список задач у отсутствующего пользователя: " + userName)
        );
        return formatTaskListResponse(user.getTasks());
    }

    public void createTask(String userName, String taskName) {
        repository.getTaskByName(taskName).ifPresent(user -> {
            throw new AnyOtherErrorException("Уже существует задача с таким именем: " + taskName);
        });
        repository.getUserByName(userName).ifPresentOrElse(user -> {
            Set<Task> tasks = new LinkedHashSet<>(user.getTasks());
            tasks.add(new Task(taskName, CREATED));
            repository.updateUserTaskList(user, tasks);
        }, () -> repository.saveNewUser(new User(userName, new Task(taskName, CREATED))));
    }

    public User getUserByName(String userName) {
        return repository.getUserByName(userName).orElseThrow(
                () -> new AnyOtherErrorException("Не найден пользователь с указанным именем: " + userName));
    }

    private Optional<Task> getTaskByName(String taskName) {
        return repository.getTaskByName(taskName);
    }

    public Task getTaskByNameWithValidate(String taskName) {
        return getTaskByName(taskName).orElseThrow(
                () -> new AnyOtherErrorException("Не найдено задачи с именем: " + taskName));
    }
}
