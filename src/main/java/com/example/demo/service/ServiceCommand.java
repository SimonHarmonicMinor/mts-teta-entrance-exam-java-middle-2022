package com.example.demo.service;

import com.example.demo.commands.*;
import com.example.demo.entity.RequestEntity;
import com.example.demo.entity.Status;
import com.example.demo.exception.WrongFormatException;
import com.example.demo.parser.ParseLine;
import com.example.demo.repo.TaskRepository;
import com.example.demo.repo.UserRepository;
import com.example.demo.utils.UserValidator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ServiceCommand {

    private final TaskRepository taskRepository = new TaskRepository();
    private final UserRepository userRepository = new UserRepository();
    private final UserValidator userValidator = new UserValidator(taskRepository, userRepository);
    private final ParseLine parseLine = new ParseLine();

    private final Map<String, CommandInterface> commands = new HashMap<>() {{
        put("CREATE_TASK", new TaskCreate(taskRepository, userRepository, userValidator));
        put("DELETE_TASK", new TaskDelete(taskRepository, userValidator));
        put("LIST_TASK", new TaskList(taskRepository, userValidator));
        put("CLOSE_TASK", new TackClose(taskRepository, userValidator));
        put("REOPEN_TASK", new TaskReopen(taskRepository, userValidator));

    }};

    private final CommandsHolder commandsHolder = new CommandsHolder(commands);

    public ServiceCommand() {
    }

    public String responseService(String request) throws IOException {

        RequestEntity parseLineResult;
        CommandInterface command;
        try {
            parseLineResult = parseLine.getResult(request);
            command = commandsHolder.getCommand(parseLineResult.getCommand());
        } catch (WrongFormatException ex) {
            return Status.WRONG_FORMAT.toString();
        }

        return command.execute(parseLineResult);
    }
}
