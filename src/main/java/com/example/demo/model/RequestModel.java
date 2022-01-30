package com.example.demo.model;

import com.example.demo.service.ValidationService;

public class RequestModel {
    private String userName;
    private CommandType commandType;
    private String argument;

    public RequestModel(String request) throws Exception {
        String[] requestParts = request.split(" ");
        ValidationService.validateRequest(requestParts);
        this.userName = requestParts[0];
        this.commandType = CommandType.valueOf(requestParts[1]);
        this.argument = requestParts[2];
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }
}
