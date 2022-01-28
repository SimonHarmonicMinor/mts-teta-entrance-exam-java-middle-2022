package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.repository.TaskRepositoryImpl;
import com.example.demo.service.RequestChecker;

public class RightChecker implements RequestChecker {

    TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();

    @Override
    public void check(Request request) {
        Command currentCommand = request.getCommand();
        if (currentCommand.equals(Command.CLOSE_TASK) || currentCommand.equals(Command.DELETE_TASK) || currentCommand.equals(Command.REOPEN_TASK)) {

        }
    }
}
