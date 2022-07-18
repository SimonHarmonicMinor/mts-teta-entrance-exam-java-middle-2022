package com.example.demo.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {

    public static Map<String, Status> taskStatusData = new HashMap<>();
    public static Map<String, List<String>> usersTaskData = new HashMap<>();

    public static Boolean checkAvailabilityTask(String nameTask) {
        try {
            return taskStatusData.containsKey(nameTask);
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public static Boolean checkPermissionTask(String user, String task) {
        List<String> tasks = Data.getUsersTaskData().get(user);
        try {
            return !tasks.contains(task);
        } catch (NullPointerException ex) {
            return true;
        }
    }

    public static Map<String, Status> getTaskStatusData() {
        return taskStatusData;
    }

    public static Map<String, List<String>> getUsersTaskData() {
        return usersTaskData;
    }

    public static void setTaskStatusData(Map<String, Status> taskStatusData) {
        Data.taskStatusData = taskStatusData;
    }

    public static void setUsersTaskData(Map<String, List<String>> usersTaskData) {
        Data.usersTaskData = usersTaskData;
    }
}