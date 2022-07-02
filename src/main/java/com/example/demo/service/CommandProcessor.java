package com.example.demo.service;

import com.example.demo.exceptions.AccessDeniedException;
import com.example.demo.exceptions.ViolationOfUniquenessException;
import com.example.demo.exceptions.WrongFormatException;
import com.example.demo.exceptions.WrongTaskStatusException;
import com.example.demo.model.Command;
import com.example.demo.model.CommandResponse;
import com.example.demo.model.Task;
import com.example.demo.model.TaskStatus;
import com.example.demo.repository.Tasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CommandProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandProcessor.class);
    public static CommandProcessor INSTANCE;

    private String request;
    private String user;
    private Command command;
    private String arg = "";

    private CommandProcessor() {
    }

    public static CommandProcessor getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommandProcessor();
        }
        return INSTANCE;
    }

    public CommandProcessor init(String request) {
        this.request = request;
        return this;
    }

    public String getResponse() {
        try {
            LOGGER.info("CHECKING THE ENTERED COMMANDS");
            checkFormat();
            LOGGER.info("VERIFICATION COMPLETED");
        } catch (Exception e) {
            return CommandResponse.WRONG_FORMAT.name();
        }
        if (!command.equals(Command.__DELETE_ALL)) {
            try {
                LOGGER.info("PERMISSION VERIFICATION");
                checkPermission();
                LOGGER.info("VERIFICATION COMPLETED");
            } catch (Exception e) {
                LOGGER.error("Exception :: {}", e.getMessage());
                return CommandResponse.ACCESS_DENIED.name();
            }

            try {
                LOGGER.info("CHECKING THE EXISTENCE OF A TASK");
                checkUniqueness();
                LOGGER.info("VERIFICATION COMPLETED");
                LOGGER.info("CHECKING THE STATUS OF A TASK");
                checkStatus();
                LOGGER.info("VERIFICATION COMPLETED");
            } catch (Exception e) {
                LOGGER.error("Exception :: {}", e.getMessage());
                return CommandResponse.ERROR.name();
            }
        }

        switch (command) {
            case CREATE_TASK:
                LOGGER.info("ATTEMPT TO CREATE A TASK");
                Tasks.add(new Task(user, arg));
                LOGGER.info("TASK CREATED");
                return CommandResponse.CREATED.name();
            case DELETE_TASK:
                LOGGER.info("ATTEMPT TO DELETE A TASK");
                Tasks.getByName(arg).setStatus(TaskStatus.DELETED);
                LOGGER.info("TASK DELETED");
                return CommandResponse.DELETED.name();
            case CLOSE_TASK:
                LOGGER.info("ATTEMPT TO CLOSE A TASK");
                Tasks.getByName(arg).setStatus(TaskStatus.CLOSED);
                LOGGER.info("TASK CLOSED");
                return CommandResponse.CLOSED.name();
            case REOPEN_TASK:
                LOGGER.info("ATTEMPT TO REOPEN A TASK");
                Tasks.getByName(arg).setStatus(TaskStatus.CREATED);
                LOGGER.info("TASK REOPENED");
                return CommandResponse.REOPENED.name();
            case LIST_TASK:
                LOGGER.info("COLLECTING TASKS");
                List<Task> userTasks = Tasks.getByOwner(arg);
                LOGGER.info("THE TASKS ARE COLLECTED");
                return CommandResponse.TASKS.name() + " " + userTasks.toString();
            case __DELETE_ALL:
                LOGGER.info("TRYING TO CLEAR TASKS");
                Tasks.clearAll();
                LOGGER.info("TASKS CLEARED");
                return "Cleared all tasks";
            default:
                return CommandResponse.ERROR.name();
        }
    }

    private void checkFormat() {
        if (request.equals(Command.__DELETE_ALL.name())) {
            command = Command.__DELETE_ALL;
        } else {
            String[] commandLine = request.split(" ");
            if (commandLine.length > 3) {
                throw new WrongFormatException("The command is incorrect (too many values, enter less than 3 words)");
            }
            user = commandLine[0];
            command = Command.valueOf(commandLine[1]);
            arg = commandLine[2];
        }
    }

    private void checkPermission() {
        if (command != Command.CREATE_TASK && command != Command.LIST_TASK) {
            String taskOwner = Tasks.getByName(arg).getOwner();
            if (!taskOwner.equals(user)) {
                throw new AccessDeniedException("The requested user is not the owner of the task");
            }
        }
    }

    private void checkUniqueness() {
        Task task = Tasks.getByName(arg);
        if (command == Command.CREATE_TASK && task != null && task.getStatus() != TaskStatus.DELETED) {
            throw new ViolationOfUniquenessException("The requested user is not the owner of the task\n");
        }
    }

    private void checkStatus() {
        Task task = Tasks.getByName(arg);
        if ((task == null || task.getStatus() != TaskStatus.CLOSED)) {
            if (command == Command.DELETE_TASK)
                throw new WrongTaskStatusException("It is not possible to delete an unclosed task");
            else if (command == Command.REOPEN_TASK)
                throw new WrongTaskStatusException("Unable to reopen an unclosed task");
        } else if (command == Command.CLOSE_TASK && task.getStatus() != TaskStatus.CREATED) {
            throw new WrongTaskStatusException("Unable to close an inactive task");
        }
    }
}