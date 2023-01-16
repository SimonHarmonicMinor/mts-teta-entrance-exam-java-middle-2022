package com.example.demo.commands;

import com.example.demo.TaskList;

public class DeleteAll implements Command {
    @Override
    public String execute(String[] parsedCommand) {
        TaskList.taskList.clear();
        return "OK";
    }
}
