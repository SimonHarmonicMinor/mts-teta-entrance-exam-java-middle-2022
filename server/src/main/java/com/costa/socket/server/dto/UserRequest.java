package com.costa.socket.server.dto;

import com.costa.socket.server.model.TaskCommand;

public class UserRequest {
    private final String userName;
    private final TaskCommand command;
    private final String arg;

    public UserRequest(String userName, TaskCommand command, String arg) {
        this.userName = userName;
        this.command = command;
        this.arg = arg;
    }

    public String getUserName() {
        return userName;
    }

    public TaskCommand getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "userName='" + userName + '\'' +
                ", command=" + command +
                ", arg='" + arg + '\'' +
                '}';
    }
}
