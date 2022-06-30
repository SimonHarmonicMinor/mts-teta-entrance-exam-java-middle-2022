package com.example.demo.service;

import com.example.demo.model.enums.Command;
import com.example.demo.model.CommandRequest;

public class FormatService {
    public CommandRequest parseRequest(String request) throws Exception {
        if ("__DELETE_ALL".equals(request)) {
            return new CommandRequest(null, Command.__DELETE_ALL, null);
        }
        String[] req = request.split(" ");
        String userName = req[0];
        Command command;
        try {
            command = Command.valueOf(req[1]);
        } catch (Exception e) {
            throw new Exception("Incorrect command");
        }
        String arg = req[2];
       if (Command.CREATE_TASK.equals(command) //|| Command.LIST_TASK.equals(command)
       ) {
            if (arg.isEmpty()) {
                throw new Exception("No command argument for " + command + "!");
            }
        }
        return new CommandRequest(userName, command, arg);
    }
}
