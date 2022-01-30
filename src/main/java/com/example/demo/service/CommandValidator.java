package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.model.Request;
import com.example.demo.model.Response;
import com.example.demo.repository.TasksRepository;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public class CommandValidator {
    private static final Logger LOG = LoggerFactory.getLogger(CommandValidator.class);

    private final UserRepository userRepository;
    private final TasksRepository tasksRepository;

    private final Map<Request.Command, BiFunction<String, String, Response>> validations = Map.of(
            Request.Command.CREATE_TASK, this::onCreate,
            Request.Command.CLOSE_TASK, this::onClose,
            Request.Command.REOPEN_TASK, this::onReopen,
            Request.Command.DELETE_TASK, this::onDelete,
            Request.Command.LIST_TASK, this::onList);

    public CommandValidator(UserRepository userRepository, TasksRepository tasksRepository) {
        this.userRepository = userRepository;
        this.tasksRepository = tasksRepository;
    }

    public Response validate(Request request) {
        return validations.get(request.getCommand()).apply(request.getUser(), request.getArg());
    }

    /**
     * Validate CREATE_TASK command
     */
    private Response onCreate(String userName, String taskName) {
        Optional<Task> taskByName = tasksRepository.findFirstByNameAndStateNot(taskName, Task.State.DELETED);
        if(taskByName.isPresent()) {
            LOG.error("Found an undeleted task with same name '" + taskName + "'");
            return new Response(Response.Result.ERROR);
        }
        return null;
    }

    /**
     * Validate CLOSE_TASK command
     */
    private Response onClose(String userName, String taskName) {
        Optional<Task> taskByName = tasksRepository.findFirstByNameAndStateNot(taskName, Task.State.DELETED);
        if(taskByName.isEmpty()) {
            LOG.error("Task '" + taskName + "' not found");
            return new Response(Response.Result.ERROR);
        }

        Task task = taskByName.get();
        if(!task.getUser().getName().equals(userName)) {
            LOG.error("User '" + userName + "' doesn't have rights to task '" + taskName + "'");
            return new Response(Response.Result.ACCESS_DENIED);
        }

        if(!task.getState().equals(Task.State.CREATED)) {
            LOG.error("Task '" + taskName + "' with state '" + task.getState() + "' can't be closed");
            return new Response(Response.Result.ERROR);
        }

        return null;
    }

    /**
     * Validate REOPEN_TASK command
     */
    private Response onReopen(String userName, String taskName) {
        Optional<Task> taskByName = tasksRepository.findFirstByNameAndStateNot(taskName, Task.State.DELETED);
        if(taskByName.isEmpty()) {
            LOG.error("Task '" + taskName + "' not found");
            return new Response(Response.Result.ERROR);
        }

        Task task = taskByName.get();
        if(!task.getUser().getName().equals(userName)) {
            LOG.error("User '" + userName + "' doesn't have rights to task '" + taskName + "'");
            return new Response(Response.Result.ACCESS_DENIED);
        }

        if(!task.getState().equals(Task.State.CLOSED)) {
            LOG.error("Task '" + taskName + "' with state '" + task.getState() + "' can't be reopened or deleted");
            return new Response(Response.Result.ERROR);
        }

        return null;
    }

    /**
     * Validate DELETE_TASK command
     */
    private Response onDelete(String userName, String taskName) {
        Optional<Task> taskByName = tasksRepository.findFirstByNameAndStateNot(taskName, Task.State.DELETED);
        if(taskByName.isEmpty()) {
            LOG.error("Task '" + taskName + "' not found");
            return new Response(Response.Result.ERROR);
        }

        Task task = taskByName.get();
        if(!task.getUser().getName().equals(userName)) {
            LOG.error("User '" + userName + "' doesn't have rights to task '" + taskName + "'");
            return new Response(Response.Result.ACCESS_DENIED);
        }

        if(!task.getState().equals(Task.State.CLOSED)) {
            LOG.error("Task '" + taskName + "' with state '" + task.getState() + "' can't be reopened or deleted");
            return new Response(Response.Result.ERROR);
        }

        return null;
    }

    /**
     * Validate LIST_TASK command
     */
    private Response onList(String userName, String anotherUserName) {
        Optional<User> userByName = userRepository.findFirstByName(anotherUserName);
        if(userByName.isEmpty()) {
            LOG.error("User '" + anotherUserName + "' not found");
            return new Response(Response.Result.ERROR);
        }

        return null;
    }
}
