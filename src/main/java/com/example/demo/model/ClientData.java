package com.example.demo.model;

import com.example.demo.model.enums.Command;

import java.util.Objects;
import java.util.StringJoiner;

public class ClientData {

    private String userName;
    private Command command;
    private String arg;

    public ClientData(String[] data) {
        this.userName = data[0];
        this.command = Command.valueOf(data[1]);
        if (!command.equals(Command.LIST_TASK)) {
            this.arg = data[2];
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClientData that = (ClientData) o;
        return Objects.equals(userName, that.userName)
                && command == that.command
                && Objects.equals(arg, that.arg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, command, arg);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ClientData.class.getSimpleName() + "[", "]")
                .add("userName='" + userName + "'")
                .add("command=" + command)
                .add("arg='" + arg + "'")
                .toString();
    }
}
