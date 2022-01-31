package com.example.demo.service.command;

import com.example.demo.service.dto.CommandParams;
import com.example.demo.service.dto.CommandType;
import com.example.demo.service.mapper.CommandMapper;
import com.example.demo.service.task.TaskService;
import com.example.demo.service.validation.ValidationService;

public class CommandServiceImpl implements CommandService {
    private CommandMapper commandMapper;
    private TaskService taskService;
    private ValidationService validationService;

    public CommandServiceImpl(CommandMapper commandMapper, TaskService taskService, ValidationService validationService) {
        this.commandMapper = commandMapper;
        this.taskService = taskService;
        this.validationService = validationService;
    }

    @Override
    public String execute(String command) {
        try {
            if (!validationService.validateRequest(command)) {
                return "WRONG_FORMAT";
            }

            CommandParams commandParams = commandMapper.toCommandParams(command);
            if (CommandType.CREATE_TASK.name().equals(commandParams.getCommandType())) {
                return taskService.createTask(commandParams);
            }

            if (CommandType.DELETE_TASK.name().equals(commandParams.getCommandType())) {
                return taskService.deleteTask(commandParams);
            }

            if (CommandType.CLOSE_TASK.name().equals(commandParams.getCommandType())) {
                return taskService.closeTask(commandParams);
            }

            if (CommandType.REOPEN_TASK.name().equals(commandParams.getCommandType())) {
                return taskService.reOpenTask(commandParams);
            }

            if (CommandType.LIST_TASK.name().equals(commandParams.getCommandType())) {
                return taskService.listTask(commandParams);
            }
            return "ERROR";
        } catch (Exception e) {
            return "ERROR";
        }
    }
}