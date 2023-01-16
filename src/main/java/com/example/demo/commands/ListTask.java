package com.example.demo.commands;

import com.example.demo.TaskList;
import com.example.demo.UserTaskList;

public class ListTask implements Command {
    @Override
    public String execute(String[] parsedCommand) {
        UserTaskList userTaskList = TaskList.taskList.get(parsedCommand[2]);

        if (userTaskList == null || userTaskList.getTaskList().isEmpty()) return "TASKS []";
        return "TASKS [" + userTaskList + "]";
    }
}
