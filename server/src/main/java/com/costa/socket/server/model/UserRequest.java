package com.costa.socket.server.model;

/**
 * DTO for user request
 */
public class UserRequest {
    private final User user;
    private final AvailableCommand command;
    private final String arg;

    public UserRequest(User user, AvailableCommand command, String arg) {
        this.user = user;
        this.command = command;
        this.arg = arg;
    }

    public User getUser() {
        return user;
    }

    public AvailableCommand getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "user=" + user +
                ", command=" + command +
                ", arg='" + arg + '\'' +
                '}';
    }
}
