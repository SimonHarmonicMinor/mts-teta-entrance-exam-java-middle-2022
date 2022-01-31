package com.example.demo.service.impl;

import com.example.demo.exception.DemoException;
import com.example.demo.model.Command;
import com.example.demo.model.Task;
import com.example.demo.model.TaskStatus;
import com.example.demo.service.TaskService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
public class TaskServiceImpl implements TaskService {

    /**
     * Map with tasks, where
     * key - task name,
     * value - task
     */
    protected final Map<String, Task> taskMap = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Task getTaskByName(String taskName) {
        if (taskMap.containsKey(taskName)) {
            return taskMap.get(taskName);
        } else {
            throw new DemoException("Task with name " + taskName + " does not found");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Task> getActualTasksByUserName(String userName) {
        return taskMap.values()
                .stream()
                .filter(task -> task.getUserName().equals(userName)
                        && task.getStatus() != TaskStatus.DELETED)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task createTask(String userName, String taskName) {
        // Getting not deleted tasks
        Map<String, Task> actualTask = taskMap.entrySet()
                .stream()
                .filter(task -> task.getValue().getStatus() != TaskStatus.DELETED)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        // Checking that the task name is unique
        if (actualTask.containsKey(taskName)) {
            throw new DemoException("Task names must be unique: taskName = " + taskName);
        }

        Task newTask = new Task(taskName, userName, TaskStatus.CREATED);
        taskMap.put(taskName, newTask);

        return newTask;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskStatus changeTaskStatus(String taskName, Command command) {
        Task task = taskMap.get(taskName);

        // Checking that task in DELETED can no longer transition to any state
        if (task.getStatus() == TaskStatus.DELETED) {
            throw new DemoException("A task in DELETED can no longer transition to any state: taskName = " + taskName);
        }

        switch (command) {
            case CLOSE_TASK: {
                task.setStatus(TaskStatus.CLOSED);
                taskMap.put(task.getName(), task);
                return TaskStatus.CLOSED;
            }
            case REOPEN_TASK: {
                task.setStatus(TaskStatus.CREATED);
                taskMap.put(task.getName(), task);
                return TaskStatus.CREATED;
            }
            case DELETE_TASK: {
                // Checking that Task in the CREATED status cannot immediately change to DELETED
                if (task.getStatus() == TaskStatus.CREATED) {
                    throw new DemoException("Task in the CREATED status cannot immediately change to DELETED: taskName = " + taskName);
                }
                task.setStatus(TaskStatus.DELETED);
                taskMap.put(task.getName(), task);
                return TaskStatus.DELETED;
            }
            default: {
                throw new DemoException("Unknown command " + command);
            }
        }
    }

}
