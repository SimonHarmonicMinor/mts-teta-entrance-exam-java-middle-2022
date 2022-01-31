package com.example.demo.service;

import com.example.demo.commands.CommandInterface;
import com.example.demo.exception.WrongFormatException;

import java.util.Map;

public class CommandsHolder {

    private final Map<String, CommandInterface> commands;

    public CommandsHolder(Map<String, CommandInterface> commands) {
        this.commands = commands;
    }

    public CommandInterface getCommand(String command) {
        CommandInterface cmd = commands.get(command);
        if (cmd == null) {
            throw new WrongFormatException();
        }
        return cmd;
    }
}
