package com.costa.socket.server.service.impl;

import com.costa.socket.server.daoservice.TaskService;
import com.costa.socket.server.model.Task;
import com.costa.socket.server.model.TaskState;
import com.costa.socket.server.model.UserRequest;
import com.costa.socket.server.service.Validator;
import com.costa.util.StringUtil;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Validator of commands and tasks
 */
public class CommandValidator implements Validator {
    private final TaskService taskService;

    public CommandValidator(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public boolean validateComposition(String command) {
        if (StringUtil.isEmpty(command))
            return false;

        Pattern pattern = Pattern.compile("\\w+\\s\\w+\\s\\w+");
        return pattern.matcher(command).matches();
    }

    @Override
    public boolean validateAccess(UserRequest request) {
        Optional<Task> task = taskService.findByDescription(request.getArg());

        boolean result;
        switch (request.getCommand()) {
            case CREATE_TASK:
                result = task.isEmpty();
                break;
            case REOPEN_TASK:
            case DELETE_TASK:
                result = task.isPresent()
                        && task.get().getUser().getName().equals(request.getUser().getName())
                        && task.get().getState().equals(TaskState.CLOSED);
                break;
            case CLOSE_TASK:
                result = task.isPresent()
                        && task.get().getUser().getName().equals(request.getUser().getName())
                        && task.get().getState().equals(TaskState.CREATED);
                break;
            default:
                throw new IllegalArgumentException("No one validator for command " + request.getCommand());
        }

        return result;
    }
}
