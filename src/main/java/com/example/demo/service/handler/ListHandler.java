package com.example.demo.service.handler;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.model.Request;
import com.example.demo.model.Response;
import com.example.demo.repository.TasksRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

public class ListHandler implements Handler {
    private static final Logger LOG = LoggerFactory.getLogger(ListHandler.class);

    private final UserRepository userRepository;
    private final TasksRepository tasksRepository;
    private final Validator validator;

    public ListHandler(UserRepository userRepository, TasksRepository tasksRepository, Validator validator) {
        this.userRepository = userRepository;
        this.tasksRepository = tasksRepository;
        this.validator = validator;
    }

    @Override
    public Response handle(String userName, String anotherUserName) {
        Response validateResult = validate(anotherUserName);
        if(!validateResult.isSuccessful())
            return validateResult;

        return run(userName, anotherUserName);
    }

    @Override
    public Request.Command getCommand() {
        return Request.Command.LIST_TASK;
    }

    private Response validate(String userName) {
        if(!validator.validateUserIsPresent(userName)) {
            LOG.error("User '" + userName + "' not found");
            return new Response(Response.Result.ERROR, false);
        }
        return new Response(true);
    }

    private Response run(String userName, String anotherUserName) {
        User anotherUser = userRepository.findFirstByName(anotherUserName).get();
        String taskList = tasksRepository.findAllByUserAndStateNot(anotherUser, Task.State.DELETED)
                .stream().map(Object::toString).collect(Collectors.joining(", "));

        return new Response(Response.Result.TASKS, "[" + taskList + "]", true);
    }
}
