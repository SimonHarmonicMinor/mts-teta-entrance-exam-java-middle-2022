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
        log.debug("Service initiated");
    }

    @Override
    public Task findByName(String taskName) {
        log.debug("start finding by taskName");
        return taskRepository.findByTaskName(taskName);
    }

    @Override
    public List<Task> findAllByUser(User user) {
        log.debug("start finding by all tasks by user");
        return taskRepository.findByUser(user);
    }

    @Override
    public Result createTask(String taskName, User user) {
        if (taskRepository.isContainedByTaskName(taskName)) {
            log.debug("Task name is busy");
            return Result.ERROR;
        }

        taskRepository.create(new Task(taskName, user));
        return Result.CREATED;
    }

    @Override
    public Result openTask(String taskName, User user) {
        log.debug("start opening task");

        Task task = taskRepository.findByTaskName(taskName);
        Result checkinResult = isExistAndUserIsOwner(task, user);
        if (checkinResult != null) {
            return checkinResult;
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
        log.debug("start closing task");

        Task task = taskRepository.findByTaskName(taskName);
        Result checkinResult = isExistAndUserIsOwner(task, user);
        if (checkinResult != null) {
            return checkinResult;
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
        log.debug("start deleting task");

        Task task = taskRepository.findByTaskName(taskName);
        Result checkinResult = isExistAndUserIsOwner(task, user);
        if (checkinResult != null) {
            return checkinResult;
        }

        // проверка статуса
        if (task.getStatus().equals(TaskStatus.CLOSED)) {
            taskRepository.findByTaskName(taskName).delete();
            taskRepository.delete(task);

            return Result.DELETED;
        }

        return Result.ERROR;
    }

    private Result isExistAndUserIsOwner(Task task, User user) {
        if (task == null) {
            log.debug("Task not found by task name");
            return Result.ERROR;
        }

        log.debug("Check access");
        if (!task.getUser().equals(user)) {
            log.debug("User is not owner task");
            return Result.ACCESS_DENIED;
        }

        log.debug("User is owner task");
        return null;
    }
}
