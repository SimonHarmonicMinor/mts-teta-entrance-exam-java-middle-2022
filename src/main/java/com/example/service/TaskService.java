package com.example.service;

import com.example.entities.Task;
import com.example.entities.User;

import java.util.ArrayList;

import static com.example.data.Data.allTasks;
import static com.example.data.Data.allUsers;

public class TaskService {

    private final UserService userService = new UserService();

    public String checkAndChangeStatus(String userName, String taskName, String newStatus) {
        String resultString = "";
        if (userService.getUserObjFromListByUserName(userName).checkUserHaveCurrentTaskByTaskName(taskName)) {
            String res = userService.getTaskFromListUsersByTaskName(taskName).changeStatus(newStatus);
            if (res.equals("")) {
                System.out.println("ERROR");
            } else {
                resultString = res;
            }
        } else {
            resultString = "ACCESS_DENIED";
        }
        return resultString;
    }

    public String createNewTask(String userName, String taskName) {
        String resultString;
        if (allTasks.contains(taskName)) {
            resultString = "ERROR";
        } else {
            userService.getUserObjFromListByUserName(userName).getTaskList().add(new Task(taskName));
            allTasks.add(taskName);
            resultString = "CREATED";
        }
        return resultString;
    }

    public void deleteTaskEverywhere(String userName, String taskName) {
        boolean canCont = false;
        for (User allUser : allUsers) {
            if (allUser.checkUserHaveCurrentTaskByTaskName(taskName)
                    && allUser.getUserName().equals(userName)) {
                for (int j = 0; j < allUser.getTaskList().size(); j++) {
                    if (allUser.getTaskList().get(j).getTaskName().equals(taskName) && allUser.getTaskList().get(j).getTaskStatus().equals("DELETED")) {
                        allUser.getTaskList().remove(j);
                        canCont = true;
                        break;
                    }
                }
            }
        }
        if (canCont) {
            for (int i = 0; i < allTasks.size(); i++) {
                if (allTasks.get(i).equals(taskName)) {
                    allTasks.remove(i);
                    break;
                }
            }
        }
    }


    public String printAllTaskCurrentUser(String arg) {
        String resultString;
        String start = "TASKS [";
        String end = "]";
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> allCorrectTasks = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getUserName().equals(arg)) {
                for (int i = 0; i < user.getTaskList().size(); i++) {
                    if (!user.getTaskList().get(i).getTaskStatus().equals("DELETED")) {
                        allCorrectTasks.add(user.getTaskList().get(i).getTaskName());
                    }
                }
            }
        }
        if (allCorrectTasks.size() == 0) {
            stringBuilder.append(start).append(end);
        } else if (allCorrectTasks.size() == 1) {
            stringBuilder.append(start).append(allCorrectTasks.get(0)).append(end);
        } else if (allCorrectTasks.size() == 2) {
            stringBuilder.append(start).append(allCorrectTasks.get(0)).append(", ").append(allCorrectTasks.get(1)).append(end);
        } else {
            stringBuilder.append(start);
            for (int i = 0; i < allCorrectTasks.size(); i++) {
                if (i < allCorrectTasks.size() - 1) {
                    stringBuilder.append(allCorrectTasks.get(i)).append(", ");
                } else {
                    stringBuilder.append(allCorrectTasks.get(i));
                }
            }
            stringBuilder.append("]");
        }
        resultString = stringBuilder.toString();
        return resultString;
    }
}