package com.example.demo.impl;

import com.example.demo.ICommand;
import com.example.demo.enums.ECommands;
import com.example.demo.enums.EResult;

import java.util.Arrays;
import java.util.List;

public class Command implements ICommand {
    private final String user;
    private final String command;
    private String args;

    public Command(String input) throws Exception {
        String[] parts = input.split(" ");

        if (parts.length > 3) {
            throw new Exception(String.valueOf(EResult.WRONG_FORMAT));
        }

        if (!parts[0].toUpperCase().equals(parts[0]) || !parts[1].toUpperCase().equals(parts[1])) {
            throw new Exception(String.valueOf(EResult.WRONG_FORMAT));
        }

        checkCommand(parts[1]);

        this.user = parts[0];
        this.command = parts[1];

        if (parts.length == 3) {
            this.args = parts[2];
        }
    }

    final public String getUser() {
        return this.user;
    }

    final public String getCommand() {
        return this.command;
    }

    final public String getArgs() {
        return this.args;
    }

    private void checkCommand(String command) throws Exception {
        try {
            List<ECommands> commands = Arrays.asList(ECommands.values());
            commands.contains(ECommands.valueOf(command));
        } catch (Exception e) {
            throw new Exception(String.valueOf(EResult.ERROR));
        }
    }
}
