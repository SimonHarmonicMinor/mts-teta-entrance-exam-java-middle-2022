package com.example.demo.manager;

import com.example.demo.TaskManagerException;
import com.example.demo.entity.StatusTask;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.manager.UserManager;

import java.util.HashMap;

/**
 * Create by fkshistero on 30.01.2022.
 */
public class UserManagerImpl implements UserManager {

    private HashMap<String, User> users = new HashMap<>();

    /**
     * @throws TaskManagerException There is a problem. User already exists.
     */
    @Override
    public User createUser(String userName) throws TaskManagerException {
        if(users.containsKey(userName)) {
            throw new TaskManagerException("User already exists.");
        }

        User user =  new User(userName);
        users.put(userName, user);
        return user;
    }


    /**
     * Add a new task for a user.
     * If there is not a user that it creates a new user.
     */
    @Override
    public void addTask(String userName, String nameTask) throws TaskManagerException {

        if(!this.isUniqueness(nameTask)) {
            throw new TaskManagerException(String.format("Name task '%s' already is taken.", nameTask));
        }

        //If there is not a user that it creates a new user.
        User user = users.getOrDefault(userName, this.createUser(userName));
        Task task = new Task(nameTask);
        user.addTask(task);

    }

    @Override
    public boolean changeTaskStatus(String userName, String taskName, StatusTask newStatus) throws TaskManagerException {
        
        return false;
    }



    /**
     * It checks the uniqueness of the task considering all users.
     */
    private boolean isUniqueness(String nameTask) {
        for(User user: this.users.values()) {
            if(user.containsTask(nameTask)) {
                return false;
            }
        }
        return true;
    }
}
