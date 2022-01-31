package com.example.demo.service.mapper;

import com.example.demo.service.dto.CommandParams;
import com.example.demo.service.dto.CommandType;

public class CommandMapperImpl implements CommandMapper {
    @Override
    public CommandParams toCommandParams(String command) throws IllegalArgumentException {
        String[] params = command.split("\\s+");
        if (params.length == 3 && CommandType.LIST_TASK.name().equals(params[1])) {
            return new CommandParams(params[0], params[1], null, params[2]);
        }
        if (params.length == 3 && !CommandType.LIST_TASK.name().equals(params[1])) {
            return new CommandParams(params[0], params[1], params[2]);
        }
        if (params.length == 4) {
            return new CommandParams(params[0], params[1], params[2], params[3]);
        }
        throw new IllegalArgumentException();
    }
}