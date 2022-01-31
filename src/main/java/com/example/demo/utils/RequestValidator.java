package com.example.demo.utils;

import com.example.demo.exception.*;
import com.example.demo.model.Request;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.model.enums.Command;
import com.example.demo.model.enums.State;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestValidator {

    private static final Logger LOG = LoggerFactory.getLogger(RequestValidator.class);

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public RequestValidator(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Request getRequest(String line) throws Exception {
        String[] parsedLine = line.split(" ", -1);

        if (parsedLine.length != 3) {
            throw new WrongFormatException(line);
        }

        Request request = new Request();

        String user = parsedLine[0];
        String command = parsedLine[1];
        String arg = (parsedLine[2]);

        User userEntity = userRepository.getUser(user);

        //User not found exception
        if (userEntity == null) {
            throw new UserNotFoundException(user);
        }

        request.setUser(userEntity);

        Command commandEnum;

        //Wrong command
        try {
            commandEnum = Command.valueOf(command);
        } catch (Exception e) {
            throw new WrongCommandException(command);
        }

        request.setCommand(commandEnum);

        //Command.LIST_TASKS validation
        if (Command.LIST_TASK.name().equals(command)) {
            User user2 = userRepository.getUser(arg);
            if (user2 == null) {
                throw new UserNotFoundException(arg);
            }

            request.setListedUser(user2);

            return request;
        }

        Task task = taskRepository.getTask(arg);

        //Command.CREATE_TASK validation
        if (Command.CREATE_TASK.equals(commandEnum)) {
            if (!isTaskDeleted(task)) {
                throw new TaskAlreadyExistsException(arg);
            }

            request.setTaskName(arg);

            return request;
        }

        //Command.CLOSE_TASK/REOPEN_TASK/DELETE_TASK validation

        if (!userEntity.hasTask(task)) {
            throw new AccessDeniedException();
        }

        if (!isValidChangeStateRequest(task, commandEnum)) {
            throw new ChangeStateException(command);
        }

        request.setTask(task);

        LOG.info("Request successfully validated: {}", line);

        return request;
    }

    private boolean isValidChangeStateRequest(Task task, Command command) {
        return State.CREATED.equals(task.getState()) && Command.CLOSE_TASK.equals(command)
                || State.CLOSED.equals(task.getState()) && Command.REOPEN_TASK.equals(command)
                || State.CLOSED.equals(task.getState()) && Command.DELETE_TASK.equals(command);
    }

    private boolean isTaskDeleted(Task task) {
        return task == null || State.DELETED.equals(task.getState());
    }

}
