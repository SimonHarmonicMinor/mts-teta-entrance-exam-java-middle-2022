package com.example.demo.service;

import com.example.demo.enums.Command;
import com.example.demo.exceptions.AccessDeniedException;
import com.example.demo.exceptions.TaskProcessingException;
import com.example.demo.exceptions.WrongFormatException;
import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.example.demo.enums.Response.ACCESS_DENIED;
import static com.example.demo.enums.Response.CLOSED;
import static com.example.demo.enums.Response.CREATED;
import static com.example.demo.enums.Response.DELETED;
import static com.example.demo.enums.Response.ERROR;
import static com.example.demo.enums.Response.REOPENED;
import static com.example.demo.enums.Response.TASKS;
import static com.example.demo.enums.Response.WRONG_FORMAT;

public class RequestHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RequestHandler.class);

    public static final RequestHandler INSTANCE = new RequestHandler();

    private final TaskRepository repository;

    private RequestHandler() {
        repository = TaskRepository.INSTANCE;
    }

    public String handle(String line) {
        CommandLine commandLine;
        try {
            commandLine = validate(line);
        } catch (WrongFormatException e) {
            return WRONG_FORMAT.name();
        }

        try {
            switch (commandLine.command) {
                case CREATE_TASK:
                    repository.create(commandLine.task);
                    return CREATED.name();
                case DELETE_TASK:
                    repository.delete(commandLine.task);
                    return DELETED.name();
                case CLOSE_TASK:
                    repository.close(commandLine.task);
                    return CLOSED.name();
                case REOPEN_TASK:
                    repository.reopen(commandLine.task);
                    return REOPENED.name();
                case LIST_TASK:
                    return TASKS.name() + " " + printTasks(repository.getTasks(commandLine.owner));
                default:
                    LOG.error("Can't find command after validation");
                    return ERROR.name();
            }
        } catch (TaskProcessingException e) {
            LOG.error("Error: ", e);
            return ERROR.name();
        } catch (AccessDeniedException e) {
            return ACCESS_DENIED.name();
        }
    }

    private String printTasks(List<Task> tasks) {
        String str = tasks.stream()
                .map(Task::getName)
                .reduce((s1, s2) -> s1 + ", " + s2)
                .orElse("");

        return "[" + str + "]";
    }

    private CommandLine validate(String line) throws WrongFormatException {
        String[] parsed = line.split(" ");
        if (parsed.length != 3) {
            throw new WrongFormatException();
        }

        Command parsedCommand;
        try {
            parsedCommand = Command.valueOf(parsed[1]);
        } catch (Exception e) {
            throw new WrongFormatException();
        }
        
        if (!Command.ALL_COMMANDS.contains(parsedCommand)) {
            throw new WrongFormatException();
        }

        if (parsedCommand == Command.LIST_TASK) {
            return new CommandLine(parsedCommand, parsed[2]);
        }

        Task parsedTask = new Task(parsed[2], parsed[0]);
        return new CommandLine(parsedCommand, parsedTask);
    }

    private static class CommandLine {

        private final Command command;

        private Task task;

        private String owner;

        private CommandLine(Command command, Task task) {
            this.command = command;
            this.task = task;
        }

        private CommandLine(Command command, String owner) {
            this.command = command;
            this.owner = owner;
        }
    }
}
