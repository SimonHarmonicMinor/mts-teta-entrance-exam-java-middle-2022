package com.example.demo.impl;

import com.example.demo.enums.Commands;
import com.example.demo.enums.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

public final class CommandProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(CommandProcessor.class);
    private static final TaskList taskList = new TaskList();

    public String processCommand(String input) throws Exception {
        String[] parts = input.split(" ");

        if (parts.length > 3) {
            throw new Exception(String.valueOf(Result.WRONG_FORMAT));
        }

        if (!parts[0].toUpperCase().equals(parts[0]) || !parts[1].toUpperCase().equals(parts[1])) {
            throw new Exception(String.valueOf(Result.WRONG_FORMAT));
        }

        String user = parts[0];

        Commands command = mapCommand(parts[1]);
        String args = "";

        if (parts.length == 3) {
            args = parts[2];
        }

        LOG.info(command.toString());

        switch (command) {
            case LIST_TASK:
                return processGetTaskList(args);
            case CREATE_TASK:
                return processAddTask(user, args);
            case DELETE_TASK:
                return processDeleteTask(user, args);
            case REOPEN_TASK:
                return processReopenTask(user, args);
            case CLOSE_TASK:
                return processCloseTask(user, args);
            default:
                return Result.ERROR.toString();
        }
    }

    private static Commands mapCommand(String command) throws Exception {
        try {
            return Commands.valueOf(command);
        } catch (Exception e) {
            throw new Exception(String.valueOf(Result.ERROR));
        }
    }

    private static String processGetTaskList(String user) {
        var userTaskList = taskList.getTaskListForUser(user);

        var result = userTaskList.stream()
                .map(Task::getName)
                .collect(Collectors.joining(","));

        return Result.TASKS + " [" + result + "]";
    }

    private static String processAddTask(String user, String taskName) throws Exception {
        taskList.addTask(user, taskName);

        return Result.CREATED.toString();
    }

    private static String processDeleteTask(String user, String taskName) throws Exception {
        taskList.deleteTask(user, taskName);
        return Result.DELETED.toString();
    }

    private static String processReopenTask(String user, String taskName) throws Exception {
        taskList.reopenTask(user, taskName);
        return Result.REOPENED.toString();
    }

    private static String processCloseTask(String user, String taskName) throws Exception {
        taskList.closeTask(user, taskName);
        return Result.CLOSED.toString();
    }
}
