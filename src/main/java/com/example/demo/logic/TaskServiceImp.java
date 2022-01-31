package com.example.demo.logic;

import com.example.demo.model.Result;
import com.example.demo.model.Task;
import com.example.demo.model.TaskStatus;
import com.example.demo.model.User;
import com.example.demo.repo.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TaskServiceImp implements TaskService {
    private final Logger log = LoggerFactory.getLogger(TaskServiceImp.class);
    private final TaskRepository taskRepository;

    public TaskServiceImp(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        log.info("Service initiated");
    }

    @Override
    public Task findByName(String taskName) {
        log.info("start finding by taskName");
        return taskRepository.findByTaskName(taskName);
    }

    @Override
    public List<Task> findAllByUser(User user) {
        log.info("start finding by all tasks by user");
        return taskRepository.findByUser(user);
    }

    @Override
    public Result createTask(String taskName, User user) {
        if (taskRepository.isContainedByTaskName(taskName)) {
            log.info("Task name is busy");
            return Result.ERROR;
        }

        taskRepository.create(new Task(taskName, user));
        return Result.CREATED;
    }

    @Override
    public Result openTask(String taskName, User user) {
        log.info("start opening task");


        // проверка существования
        Task task = taskRepository.findByTaskName(taskName);
        if (task == null) {
            log.info("Task not found by task name");
            return Result.ERROR;
        }

        //проверка доступа
        log.info("Check access");
        if (!task.getUser().equals(user)) {
            log.info("User is not owner task");
            return Result.ACCESS_DENIED;
        }

        // проверка статуса
        if (task.getStatus().equals(TaskStatus.CLOSED)) {
            taskRepository.findByTaskName(taskName).open();

            return Result.REOPENED;
        }

        return Result.ERROR;
    }

    @Override
    public Result closeTask(String taskName, User user) {
        log.info("start closing task");

        Task task = taskRepository.findByTaskName(taskName);
        if (task == null) {
            log.info("Task not found by task name");
            return Result.ERROR;
        }

        log.info("Check access");
        if (!task.getUser().equals(user)) {
            log.info("User is not owner task");
            return Result.ACCESS_DENIED;
        }

        // проверка статуса
        if (task.getStatus().equals(TaskStatus.CREATED)) {
            taskRepository.findByTaskName(taskName).close();

            return Result.CLOSED;
        }

        return Result.ERROR;
    }

    @Override
    public Result deleteTask(String taskName, User user) {
        log.info("start deleting task");

        Task task = taskRepository.findByTaskName(taskName);
        if (task == null) {
            log.info("Task not found by task name");
            return Result.ERROR;
        }

        log.info("Check access");
        if (!task.getUser().equals(user)) {
            log.info("User is not owner task");
            return Result.ACCESS_DENIED;
        }

        // проверка статуса
        if (task.getStatus().equals(TaskStatus.CLOSED)) {
            taskRepository.findByTaskName(taskName).delete();

            return Result.DELETED;
        }

        return Result.ERROR;
    }
}
