package com.example.demo.services;

import com.example.demo.enums.CommandType;
import com.example.demo.enums.ResponseCodes;
import com.example.demo.exception.DemoAppException;
import com.example.demo.model.Response;
import com.example.demo.persistence.repository.TaskRepositoryImpl;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandService {
    private String user;
    private CommandType command;
    private String arg;
    private Response response;
    TaskRepositoryImpl taskRepositoryImpl;

    private void selectCommand() throws Exception {
        switch (command) {
            case CREATE_TASK:
                taskRepositoryImpl.createTask(user, arg, response);
                break;
            case CLOSE_TASK:
                taskRepositoryImpl.closeTask(user, arg, response);
                break;
            case DELETE_TASK:
                taskRepositoryImpl.deleteTask(user, arg, response);
                break;
            case REOPEN_TASK:
                taskRepositoryImpl.reopenTask(user, arg, response);
                break;
            case LIST_TASK:
                taskRepositoryImpl.getAllUserTasks(arg, response);
                break;
            case DELETE_ALL:
                taskRepositoryImpl.deleteAllUserTasks(response);
                break;
            default:
                response.setResponseCode(ResponseCodes.ERROR);
                break;
        }
    }

    private void parseCommand(String receivedCommand) throws DemoAppException {
        String[] dividedCommand = receivedCommand.split("\\s+");

        final Set<String> allCommands = Arrays
                .stream(CommandType.values())
                .map(Enum::name)
                .collect(Collectors.toSet());

        if (dividedCommand.length == 1 && receivedCommand.equals(CommandType.DELETE_ALL.getCommand())) {
            this.command = CommandType.DELETE_ALL;
        } else if (dividedCommand.length == 3 && allCommands.contains(dividedCommand[1])) {
            this.user = dividedCommand[0];
            this.command = CommandType.valueOf(dividedCommand[1]);
            this.arg = dividedCommand[2];
        } else if (dividedCommand.length != 3 || !allCommands.contains(dividedCommand[1])) {
            response.setResponseCode(ResponseCodes.WRONG_FORMAT);
            throw new DemoAppException("Некорректный формат команды", "WRONG_FORMAT");
        }
    }

    public String checkCommand(String receivedCommand){
        try {
            response = new Response();
            parseCommand(receivedCommand);
            taskRepositoryImpl = new TaskRepositoryImpl();
            selectCommand();
        } catch (Exception e) {
            if (Objects.isNull(response.getResponseCode()))
                response.setResponseCode(ResponseCodes.ERROR);
        }
        return response.toString();
    }
}
