package com.example.demo.manager;

import com.example.demo.TaskManagerException;
import com.example.demo.entity.StatusTask;
import com.example.demo.entity.User;

/**
 * Create by fkshistero on 30.01.2022.
 */
public interface UserManager {

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
     */
    void addTask(String userName, String nameTask) throws TaskManagerException;


    /**
     * It changes a status of a task.
     *
     * @param userName Name of a user.
     * @param taskName Name of a task.
     * @param newStatus New status of a task.
     * @return If it is a successful change, the return will be "true".
     * @throws TaskManagerException There is a problem.
     */
    boolean changeTaskStatus(String userName, String taskName, StatusTask newStatus) throws TaskManagerException;

}
