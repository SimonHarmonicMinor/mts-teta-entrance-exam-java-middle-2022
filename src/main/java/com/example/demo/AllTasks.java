package com.example.demo;

import java.util.ArrayList;
import java.util.HashSet;

public class AllTasks {
    private static String activeUser;
    private final UserTasks userTasks = new UserTasks();
    public static HashSet<String> allActiveTasks = new HashSet<>();

    public ArrayList<String> LIST(String task){
        return userTasks.LIST(task);
    }


    public TaskState CREATE(String task){
        TaskState st;
        st = userTasks.CREATE(task);
        if ( st == TaskState.CREATED) {
            allActiveTasks.add(task);
        }
        return st;
    }

    public TaskState ACTION(String task, UserAction action) {
        TaskState st = userTasks.ACTION(task, action);
        switch(st) {
            case DELETED:
            allActiveTasks.remove(task);
            break;
            case ERROR:

            break;
        }
        return st;
    }


    public static void setActiveUser(String activeUser) {
        AllTasks.activeUser = activeUser;
    }

    public static String getActiveUser() {
        return activeUser;
    }



}
