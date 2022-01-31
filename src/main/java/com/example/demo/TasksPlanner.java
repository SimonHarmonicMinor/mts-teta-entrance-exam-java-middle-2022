package com.example.demo;

import java.util.*;

public class TasksPlanner {
    Map<String, Task> listTasks;

    public TasksPlanner() {
        this.listTasks = new HashMap<>();   //key-nameTask, value-Task
    }

    public String create_task(String username, String nameTask) {
        Set<String> names = listTasks.keySet();
        if (names.contains(nameTask))   //Названия задач должны быть уникальными для всех пользователей (удаленные не учитываются).
            return "ERROR";

        listTasks.put(nameTask, new Task(nameTask, username, "CREATED"));
        return "CREATED";
    }

    public String delete_task(String username, String nameTask) {
        Task task = listTasks.get(nameTask);
        if (task == null) return "ERROR";

        if (task.getAuthor().equals(username)) {    //Пользователь может закрывать, удалять и заново открывать только свои задачи
            if (task.getStatus().equals("CLOSED")) {    //Задача проходит следующие состояния: CLOSED -> DELETED.
                listTasks.remove(nameTask);
                return "DELETED";
            } else return "ACCESS_DENIED";
        } else return "ACCESS_DENIED";
    }

    public String close_task(String username, String nameTask) {
        Task task = listTasks.get(nameTask);
        if (task == null) return "ERROR";

        if (task.getAuthor().equals(username)) {    //Пользователь может закрывать, удалять и заново открывать только свои задачи
            if (task.getStatus().equals("CREATED")) {
                task.setStatus("CLOSED");
                return "CLOSED";
            } else return "ERROR";
        } else return "ACCESS_DENIED";
    }

    public String reopen_task(String username, String nameTask) {
        Task task = listTasks.get(nameTask);
        if (task == null) return "ERROR";

        if (task.getAuthor().equals(username)) {    //Пользователь может закрывать, удалять и заново открывать только свои задачи
            if (task.getStatus().equals("CLOSED")) {
                task.setStatus("CREATED");
                return "REOPENED";
            } else return "ERROR";
        } else return "ACCESS_DENIED";
    }

    public String get_list_task(String username) {
        String tasks = "";
        for (String task : listTasks.keySet()) {
            //собираем список задач заданного юзера
            if (listTasks.get(task).getAuthor().equals(username))
                tasks += task + ", ";
        }
        if (tasks.endsWith(", ")) tasks = tasks.substring(0, tasks.length() - 2);
        return "TASKS [" + tasks + "]";
    }
}
