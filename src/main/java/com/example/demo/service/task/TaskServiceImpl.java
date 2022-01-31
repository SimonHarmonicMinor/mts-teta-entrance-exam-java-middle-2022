package com.example.demo.service.task;

import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.entity.Task;
import com.example.demo.repository.entity.TaskStatus;
import com.example.demo.repository.entity.User;
import com.example.demo.service.dto.CommandParams;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public String createTask(CommandParams commandParams) throws Exception {
        var user = new User(commandParams.getUser());
        var newTask = new Task(commandParams.getTaskName(), user, TaskStatus.CREATED);
        taskRepository.createTask(newTask);
        return "CREATED";
    }

    @Override
    public String deleteTask(CommandParams commandParams) throws Exception {
        Optional<Task> foundTask = taskRepository.findTaskByName(commandParams.getTaskName());
        if (foundTask.isEmpty()) {
            return "ERROR";
        }
        Task task = foundTask.get();
        if (!task.getUser().getName().equals(commandParams.getUser())) {
            return "ACCESS_DENIED";
        }
        if (TaskStatus.CREATED.equals(task.getStatus())) {
            return "ERROR";
        }
        task.setStatus(TaskStatus.DELETED);

        taskRepository.updateTask(task);
        return "DELETED";
    }

    @Override
    public String closeTask(CommandParams commandParams) throws Exception {
        Optional<Task> foundTask = taskRepository.findTaskByName(commandParams.getTaskName());
        if (foundTask.isEmpty()) {
            return "ERROR";
        }
        Task task = foundTask.get();
        if (!task.getUser().getName().equals(commandParams.getUser())) {
            return "ACCESS_DENIED";
        }
        if (TaskStatus.DELETED.equals(task.getStatus())) {
            return "ERROR";
        }
        task.setStatus(TaskStatus.CLOSED);
        taskRepository.updateTask(task);
        return "CLOSED";
    }


    @Override
    public String reOpenTask(CommandParams commandParams) throws Exception {
        Optional<Task> foundTask = taskRepository.findTaskByName(commandParams.getTaskName());
        if (foundTask.isEmpty()) {
            return "ERROR";
        }
        Task task = foundTask.get();
        if (!task.getUser().getName().equals(commandParams.getUser())) {
            return "ACCESS_DENIED";
        }
        if (TaskStatus.DELETED.equals(task.getStatus())) {
            return "ERROR";
        }
        task.setStatus(TaskStatus.CREATED);
        taskRepository.updateTask(task);
        return "REOPENED";
    }

    @Override
    public String listTask(CommandParams commandParams) {
        Collection<Task> tasksByUser = taskRepository.findTasksByUser(commandParams.getArg());
        if (tasksByUser.isEmpty()) {
            return "TASKS []";
        }
        List<Task> foundTasks = tasksByUser.stream()
                .filter(task -> !TaskStatus.DELETED.equals(task.getStatus())
                        || (commandParams.getUser().equals(task.getUser().getName())
                        && commandParams.getUser().equals(commandParams.getArg()))
                )
                .collect(Collectors.toList());
        if (foundTasks.isEmpty()) {
            return "TASKS []";
        }

        String response = "TASKS";
        String nameTasks = foundTasks.stream().map(Task::getName).collect(Collectors.joining(", "));
        return response + " [" + nameTasks + "]";
    }
}