package com.example.demo.service;

import com.example.demo.model.Command;
import com.example.demo.model.Task;
import com.example.demo.model.TaskStatus;

import java.util.List;

/**
 * Service for performing actions on tasks
 */
public interface TaskService {

    /**
     * Getting a task by task name
     *
     * @param taskName Task name
     * @return Task or exception if task does not found
     */
    Task getTaskByName(String taskName);

    /**
     * Getting actual tasks by user name
     *
     * @param userName User name
     * @return List of user actual tasks
     */
    List<Task> getActualTasksByUserName(String userName);

    /**
     * Creating task
     *
     * @param userName User name
     * @param taskName Task name
     * @return Created task
     */
    Task createTask(String userName, String taskName);

    /**
     * Changing task status
     *
     * @param taskName Task name
     * @param command  Command
     * @return Task status
     */
    TaskStatus changeTaskStatus(String taskName, Command command);
}
