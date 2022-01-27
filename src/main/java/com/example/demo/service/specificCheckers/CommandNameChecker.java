package com.example.demo.service.specificCheckers;

import com.example.demo.entity.Command;
import com.example.demo.entity.Request;
import com.example.demo.exception.FormatException;
import com.example.demo.service.RequestChecker;

public class CommandNameChecker implements RequestChecker {

    // команда не сопадает ни с одной из енама команд
    @Override
    public void check(Request request) {
        Command[] commandArray = Command.values();
        for (Command command : commandArray) {
            if (!command.equals(request.getCommand())) {
                throw new FormatException("Некорректная команда");
            }
        }
    }
}
