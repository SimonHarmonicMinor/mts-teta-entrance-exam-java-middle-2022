package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private static final ArrayList<Task> tasksList = new ArrayList<>();

    public static String createTask(String User, String taskTitle) {

        if(!check(taskTitle, tasksList)) {return "ERROR";}
        Task task = new Task();
        task.setCreator(User);
        task.setTitle(taskTitle);
        task.setTaskStatus("CREATED");
        tasksList.add(task);
        return "CREATED";
    }

    public static String deleteTask(String userName, String taskName) {
        if(validate(taskName, userName, tasksList)) {
            return "ACCESS_DENIED";
        }
        for(Task task : tasksList) {
            if (task.getName().equals(taskName)) {
                if (!"CREATED".equals(task.getTaskStatus())) {
                    task.setTaskStatus("DELETED");
                    return "DELETED";
                } else {
                    return "ERROR";
                }
            }
        }
        return "ERROR";
    }

    public static String closeTask(String userName, String taskName) {
        if (validate(taskName, userName, tasksList)) {
            return "ACCESS_DENIED";
        }
        for (Task task : tasksList) {
            if (task.getName().equals(taskName)) {
                if (!"CLOSED".equals(task.getTaskStatus())) {
                    task.setTaskStatus("CLOSED");
                    return "CLOSED";
                } else {
                    return "ERROR";
                }
            }
        }
        return "ERROR";
    }

    public static String reopenTask(String userName, String taskName) {
        if (validate(taskName, userName, tasksList)) {
            return "ACCESS_DENIED";
        }
        for (Task task : tasksList) {
            if (task.getName().equals(taskName)) {
                if(!"CREATED".equals(task.getTaskStatus())) {
                    task.setTaskStatus("REOPENED");
                    return "REOPENED";
                } else {
                    return "ERROR";
                }
            }
        }
        return "ERROR";
    }

    public static List<String> showTaskList(String creatorName) {
        List<String> userTasks = new ArrayList<>();
        for(Task task : tasksList) {
            if (!"DELETED".equals(task.getTaskStatus())  && task.getCreator().equals(creatorName)) {
                userTasks.add(task.getName());
            }
        }
        return userTasks;
    }

    public static boolean validateFormat(String input) {
        String[] input_array = input.split(" ");

        if (input_array.length == 3) {
            String name = input_array[0];
            String command = input_array[1];
            String arg = input_array[2];

            if(name.equals(name.toUpperCase()) && ("CREATE_TASK,DELETE_TASK,CLOSE_TASK,REOPEN_TASK,LIST_TASK").contains(command)) {
                if("LIST_TASK".equals(command)) {
                    return arg.equals(arg.toUpperCase());
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean check(String taskName, ArrayList<Task> tasksCollection) {
        for(Task task : tasksCollection) {
            if(taskName.equals(task.getName())){
                return "DELETED".equals(task.getTaskStatus());
            }
        }
        return true;
    }

    public static boolean validate(String taskName, String userName, ArrayList<Task> tasksList) {

        for(Task task : tasksList) {
            if(taskName.equals(task.getName()) &&
                    !"DELETED".equals(task.getTaskStatus()) &&
                    userName.equals(task.getCreator())) {
                return false;
            }
        }
        return true;
    }
}