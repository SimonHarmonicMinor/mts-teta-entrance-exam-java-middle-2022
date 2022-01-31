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

import java.util.Optional;

public class CreateHandler implements Handler {
    private static final Logger LOG = LoggerFactory.getLogger(CreateHandler.class);

    private final UserRepository userRepository;
    private final TasksRepository tasksRepository;
    private final Validator validator;

    public CreateHandler(UserRepository userRepository, TasksRepository tasksRepository, Validator validator) {
        this.userRepository = userRepository;
        this.tasksRepository = tasksRepository;
        this.validator = validator;
    }

    @Override
    public Response handle(String userName, String taskName) {
        Response validateResult = validate(taskName);
        if(!validateResult.isSuccessful())
            return validateResult;

        return run(userName, taskName);
    }

    @Override
    public Request.Command getCommand() {
        return Request.Command.CREATE_TASK;
    }

    private Response validate(String taskName) {
        if(!validator.validateTaskIsAbsent(taskName)) {
            LOG.error("Found an undeleted task with same name '" + taskName + "'");
            return new Response(Response.Result.ERROR, false);
        }
        return new Response(true);
    }

    private Response run(String userName, String taskName) {
        Optional<User> firstByName = userRepository.findFirstByName(userName);
        User user;
        if(firstByName.isEmpty()) {
            user = new User(userName);
            userRepository.save(user);
        }
        else
            user = firstByName.get();

        Task task = new Task(taskName, Task.State.CREATED, user);
        tasksRepository.save(task);

        return new Response(Response.Result.CREATED, true);
    }
}
