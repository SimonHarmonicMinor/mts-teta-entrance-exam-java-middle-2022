package com.example.demo.controller;

import com.example.demo.api.controller.ITaskController;
import com.example.demo.api.service.ITaskService;
import com.example.demo.enumerated.Result;
import com.example.demo.enumerated.Status;
import com.example.demo.model.Task;

import java.util.List;

import static java.util.Comparator.comparing;

public class TaskController implements ITaskController {

    private final ITaskService taskService;

    public TaskController(final ITaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public String showTaskList(String user) {
        final List<Task> tasks = taskService.findAll();
        if (tasks == null) {
            return Result.ERROR.toString();
        }
        return Result.TASKS.name() + tasks.toString();
    }

    @Override
    public String createTask(String name, String user) {
        if (taskService.findOneByName(name) != null) {
            return Result.ERROR.toString();
        }
        final Task task = taskService.add(name, user);
        if (task == null) {
            return Result.ERROR.toString();
        }
        return Result.CREATED.toString();
    }

    @Override
    public String clearTasks() {
        taskService.clear();
        return Result.DELETED.toString();
    }

    @Override
    public String removeTaskByName(String name, String user) {
        Task task = taskService.findOneByName(name);
        if (task == null || !task.getStatus().equals(Status.CLOSED)) {
            return Result.ERROR.toString();
        }
        if (!task.getUser().equals(user)) {
            return Result.ACCESS_DENIED.toString();
        }
        task = taskService.removeOneByName(name);
        if (task == null)
            return Result.ERROR.toString();
        return Result.DELETED.toString();
    }

    @Override
    public String closeTaskByName(String name, String user) {
        Task task = taskService.findOneByName(name);
        if (task == null || !task.getStatus().equals(Status.CREATED)) {
            return Result.ERROR.toString();
        }
        if (!task.getUser().equals(user)) {
            return Result.ACCESS_DENIED.toString();
        }
        task = taskService.changeTaskStatusByName(name, Status.CLOSED);
        if (task == null)
            return Result.ERROR.toString();
        return Result.CLOSED.toString();
    }

    @Override
    public String reopenTaskByName(String name, String user) {
        Task task = taskService.findOneByName(name);
        if (task == null || !task.getStatus().equals(Status.CLOSED)) {
            return Result.ERROR.toString();
        }
        if (!task.getUser().equals(user)) {
            return Result.ACCESS_DENIED.toString();
        }
        task = taskService.changeTaskStatusByName(name, Status.CREATED);
        if (task == null)
            return Result.ERROR.toString();
        return Result.CREATED.toString();
    }

    @Override
    public String showTaskListByUser(String user) {
        final List<Task> tasks = taskService.findAllByUser(user);
        if (tasks == null) {
            return Result.ERROR.toString();
        }
        tasks.sort(comparing(Task::getCreated));
        return Result.TASKS.name() + tasks.toString();
    }

}
