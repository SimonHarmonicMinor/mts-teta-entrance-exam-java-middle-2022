package com.example.demo.commands;

import com.example.demo.Task;
import com.example.demo.TaskList;
import com.example.demo.TaskStatus;
import com.example.demo.UserTaskList;

public class CreateTask implements Command {
    @Override
    public String execute(String[] parsedCommand) {
        if (TaskList.findTask(parsedCommand[2]) != null) return "ERROR";
        Task newTask = new Task(parsedCommand[0], parsedCommand[2], TaskStatus.CREATED);
        UserTaskList userTaskList = TaskList.taskList.get(parsedCommand[0]);
        if (userTaskList != null) {
            userTaskList.putTask(newTask);
        } else {
            TaskList.taskList.put(parsedCommand[0], new UserTaskList(newTask));
        }
        return "CREATED";
    }
}
