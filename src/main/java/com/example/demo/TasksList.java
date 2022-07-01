package com.example.demo;
import java.util.ArrayList;
import java.util.List;

public class TasksList {
    public static ArrayList<Task> taskslist = new ArrayList<>();

    public static int getIndexByName(String name) {
        int idx = -1;
        for(int i = 0; i< taskslist.size(); i++) {
            if (name.equals(taskslist.get(i).name) ) {
                idx = i;
                return idx;
            }
        }
        return idx;
    }

    public static boolean validateUser(String user, String name) {
        int idx = getIndexByName(name);
        if (!user.equals(taskslist.get(idx).user) ) {
            return true;
        }
        return false;
    }

    public static String createTask(Task task) {

        // Проверим на уникальность
        for(Task t : taskslist) {
            if (t.name.equals(task.name) ) {
                return "ERROR";
            }
        }

        // Все хорошо, добавим в список
        taskslist.add(task);
        return "CREATED";
    }

    public static String closeTask(Task task) {

        if (validateUser(task.user , task.name))
        {
            return "ACCESS_DENIED";
        }
        int idx = getIndexByName(task.name);

        if (idx >-1 ) {
            taskslist.set(idx, task);
            return "CLOSED";
        } else
        {
            return "ERROR";
        }
    }
    public static String deleteTask(Task task) {

        if (validateUser(task.user , task.name))
        {
            return "ACCESS_DENIED";
        }
        int idx = getIndexByName(task.name);

        if (taskslist.get(idx).state == "CREATED") {
            return "ERROR";
        }
        if (idx >-1 ) {
            taskslist.remove(idx);
            return "DELETED";
        } else
        {
            return "ERROR";
        }
    }

    public static String reopenTask(Task task) {

        if (validateUser(task.user , task.name))
        {
            return "ACCESS_DENIED";
        }
        int idx = getIndexByName(task.name);

        if (idx >-1 && (!("CREATED".equals(taskslist.get(idx).state)) )) {
            taskslist.set(idx, task);
            return "REOPENED";
        } else
        {
            return "ERROR";
        }
    }

    public static List<String> listTask(String user) {
        List<String> resultTasks = new ArrayList<>();
        for(Task t : taskslist) {
            if (("CREATED".equals(t.state) || "CLOSED".equals(t.state )) && user.equals(t.user) ) {
                resultTasks.add(t.name);
            }
        }
        return resultTasks;
    }

    public static String deleteAll() {
            taskslist.clear();
            return "ALL TASKS DELETED";
    }

}
