package com.example.demo.service.handler;

import com.example.demo.entity.Task;
import com.example.demo.model.Request;
import com.example.demo.model.Response;
import com.example.demo.repository.TasksRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReopenHandler implements Handler {
    private static final Logger LOG = LoggerFactory.getLogger(ReopenHandler.class);

    private final UserRepository userRepository;
    private final TasksRepository tasksRepository;
    private final Validator validator;

    public ReopenHandler(UserRepository userRepository, TasksRepository tasksRepository, Validator validator) {
        this.userRepository = userRepository;
        this.tasksRepository = tasksRepository;
        this.validator = validator;
    }

    @Override
    public Response handle(String userName, String taskName) {
        Response validateResult = validate(userName, taskName);
        if(!validateResult.isSuccessful())
            return validateResult;

        return run(userName, taskName);
    }

    @Override
    public Request.Command getCommand() {
        return Request.Command.REOPEN_TASK;
    }

    private Response validate(String userName, String taskName) {
        if(!validator.validateTaskIsPresent(taskName)) {
            LOG.error("Task '" + taskName + "' not found");
            return new Response(Response.Result.ERROR, false);
        }

        Task task = tasksRepository.findFirstByNameAndStateNot(taskName, Task.State.DELETED).get();
        if(!validator.validateTaskBelongsToUser(task, userName)) {
            LOG.error("User '" + userName + "' doesn't have rights to task '" + taskName + "'");
            return new Response(Response.Result.ACCESS_DENIED, false);
        }

        if(!validator.validateTaskStateMatch(task, Task.State.CLOSED)) {
            LOG.error("Task '" + taskName + "' with state '" + task.getState() + "' can't be reopened or deleted");
            return new Response(Response.Result.ERROR, false);
        }
        return new Response(true);
    }

    private Response run(String userName, String taskName) {
        Task task = tasksRepository.findFirstByNameAndStateNot(taskName, Task.State.DELETED).get();
        task.setState(Task.State.CREATED);
        tasksRepository.save(task);
        return new Response(Response.Result.REOPENED, true);
    }
}
