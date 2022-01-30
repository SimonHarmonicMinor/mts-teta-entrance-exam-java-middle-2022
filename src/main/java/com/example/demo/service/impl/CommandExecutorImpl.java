package com.example.demo.service.impl;

import com.example.demo.exception.ServiceException;
import com.example.demo.model.BaseCommandResult;
import com.example.demo.model.Command;
import com.example.demo.model.TaskListResult;
import com.example.demo.model.User;
import com.example.demo.service.CommandExecutor;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;

public class CommandExecutorImpl implements CommandExecutor {
    private final TaskService taskService = new TaskServiceImpl();
    private final UserService userService = new UserServiceImpl();
    @Override
    public BaseCommandResult execute(Command command) {
        final User commandSender = command.getUser();
        final String commandArg = command.getArgument();
        switch (command.getType()) {
            case CREATE_TASK:
                taskService.createTask(commandSender, commandArg);
                return new BaseCommandResult(BaseCommandResult.Status.CREATED);
            case CLOSE_TASK:
                taskService.closeTask(commandSender, commandArg);
                return new BaseCommandResult(BaseCommandResult.Status.CLOSED);
            case LIST_TASK:
                final User owner = commandSender.getName().equals(commandArg)
                        ? commandSender
                        : userService.findByName(commandArg).orElseThrow(() -> new ServiceException(ServiceException.ErrorType.COMMON));
                return new TaskListResult(taskService.listTasks(commandSender, owner));
            case REOPEN_TASK:
                taskService.reopenTask(commandSender, commandArg);
                return new BaseCommandResult(BaseCommandResult.Status.REOPENED);
            case DELETE_TASK:
                taskService.delete(commandSender, commandArg);
                return new BaseCommandResult(BaseCommandResult.Status.DELETED);
        }
        return new BaseCommandResult(BaseCommandResult.Status.ERROR);
    }
}
