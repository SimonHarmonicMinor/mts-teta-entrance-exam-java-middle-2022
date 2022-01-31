package com.example.demo;

import Models.Commands;
import Models.Task;
import Models.User;

import java.util.HashMap;
import java.util.Map;

public class TestClass {

    Commands commands = new Commands();
    final private Map<String, User> users = new HashMap<>();
    final private Map<String, Task> tasks = new HashMap<>();

    public String test(String word) throws Exception {
        String[] command = word.split(" ");
        if (command.length != 3) {
            return commands.getWrongFormat();
        }
        String userName = command[0];
        String procedure = command[1];
        String taskName = command[2];
        String userTask = command[2];

        switch (procedure) {
            case "CREATE_TASK":
                User user = User.getUser(users, userName);
                Task.createTask(tasks, user, taskName);
                return commands.getCreated();
            case "DELETE_TASK":
                Task.deleteTask(tasks, userName, taskName);
                return commands.getDeleted();
            case "CLOSE_TASK":
                Task.closeTask(tasks, userName, taskName);
                return commands.getClosed();
            case "REOPEN_TASK":
                Task.reopenTask(tasks, userName, taskName);
                return commands.getReopened();
            case "LIST_TASK":
                Task.getListTasks(users, userTask);
                return commands.getTasks();
            default:
                return commands.getError();
        }
    }
}

