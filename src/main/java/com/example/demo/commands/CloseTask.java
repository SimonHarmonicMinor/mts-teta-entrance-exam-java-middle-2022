package com.example.demo.commands;

import com.example.demo.Task;
import com.example.demo.TaskList;
import com.example.demo.TaskStatus;

public class CloseTask implements Command {
    @Override
    public String execute(String[] parsedCommand) {
        Task taskInWork = TaskList.findTask(parsedCommand[2]);
        if (taskInWork == null) {
            return "ERROR";
        }
        if (!taskInWork.getUserName().equals(parsedCommand[0])) {
            return "ACCESS_DENIED";
        }
        else {
            taskInWork.setTaskStatus(TaskStatus.CLOSED);
            return "CLOSED";
        }
    }
}
