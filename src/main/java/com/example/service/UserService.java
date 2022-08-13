package com.example.service;

import com.example.entities.Task;
import com.example.entities.User;

import static com.example.data.Data.allUsers;

public class UserService {
    public User getUserObjFromListByUserName(String userName) {
        User tempUser = null;
        for (User user : allUsers) {
            if (user.getUserName().equals(userName)) {
                tempUser = user;
                break;
            }
        }
        return tempUser;
    }

    public boolean checkUserAlreadyCreated(String userName) {
        boolean res = false;
        for (User user : allUsers) {
            if (user.getUserName().equals(userName)) {
                res = true;
                break;
            }
        }
        return res;
    }

    public Task getTaskFromListUsersByTaskName(String taskName) {
        Task tempTask = null;
        for (User allUser : allUsers) {
            for (int j = 0; j < allUser.getTaskList().size(); j++) {
                if (allUser.getTaskList().get(j).getTaskName().equals(taskName)) {
                    tempTask = allUser.getTaskList().get(j);
                    break;
                }
            }
        }
        return tempTask;
    }

    public void createNewUser(String userName) {
        User tempUser = new User(userName);
        allUsers.add(tempUser);
    }
}