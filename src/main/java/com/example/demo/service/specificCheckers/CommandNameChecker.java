package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.service.RequestChecker;

import java.util.EnumSet;
import java.util.Set;

public class CommandNameChecker implements RequestChecker {

    // команда не сопадает ни с одной из енама команд
    @Override
    public void check(Request request) {
        // выбрать вариант
        boolean contains = EnumSet.allOf(Command.class).contains(request.getCommand());

        if (!Set.of(Command.values()).contains(request.getCommand())) {
        }
        Command[] commandArray = Command.values();
        for (Command command : commandArray) {
            if (!command.equals(request.getCommand())) {
                throw new FormatException("Некорректная команда");
            }
        }
    }
}
