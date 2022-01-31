package com.example.demo.service.validation;

import com.example.demo.service.dto.CommandType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean validateRequest(String command) {
        if (command == null) {
            return false;
        }
        String[] commandParams = command.split("\\s+");
        if (commandParams.length > 4 || commandParams.length < 3) {
            return false;
        }
        List<String> commandTypeNames = Stream.of(CommandType.values())
                .map(CommandType::name)
                .collect(Collectors.toList());
        return commandTypeNames.contains(commandParams[1]);
    }
}