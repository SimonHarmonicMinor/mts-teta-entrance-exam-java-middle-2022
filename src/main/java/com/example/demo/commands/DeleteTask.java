package com.example.demo.commands;

import com.example.demo.Task;
import com.example.demo.TaskList;
import com.example.demo.TaskStatus;

public class DeleteTask implements Command {
    @Override
    public String execute(String[] parsedCommand) {
        Task taskInWork = TaskList.findTask(parsedCommand[2]);
        if (taskInWork == null) {
            return "ERROR";
        }
        if (!taskInWork.getUserName().equals(parsedCommand[0])) {
            return "ACCESS_DENIED";
        }
        if (!taskInWork.getTaskStatus().equals(TaskStatus.CLOSED)) return "ERROR";
        else {
            TaskList.taskList.get(parsedCommand[0]).getTaskList().remove(taskInWork);
            return "DELETED";
        }

    }
}
