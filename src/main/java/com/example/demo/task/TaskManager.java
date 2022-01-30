package com.example.demo.task;

import com.example.demo.task.entity.User;

import java.util.List;

/**
 *
 * Create by fkshistero on 30.01.2022.
 */
public interface TaskManager {

    /**
     * It adds a new user.
     *
     * @param userName Name of a new user.
     * @return A new user.
     * @throws TaskManagerException There is a problem.
     */
    User createUser(String userName) throws TaskManagerException;

    /**
     * Add a new task for a user.
     *
     * @param userName Name of a user.
     * @param nameTask Name of a new task.
     * @throws TaskManagerException There is a problem adding a task.
     * @return
     */
    boolean addTask(String userName, String nameTask) throws TaskManagerException;


    /**
     * Show all tasks of a user.
     *
     * @param userName Name of a user.
     * @return List tasks of a user.
     */
    List<String> getAllNameTasksOfUser(String userName) throws TaskManagerException;

    boolean deleteTask(String userName, String taskName) throws TaskManagerException;

    boolean closeTask(String userName, String taskName) throws TaskManagerException;

    boolean openTask(String userName, String taskName) throws TaskManagerException;
}
