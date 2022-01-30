package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.model.Request;
import com.example.demo.model.Response;
import com.example.demo.repository.TasksRepository;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class RequestHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RequestHandler.class);

    private final UserRepository userRepository;
    private final TasksRepository tasksRepository;
    private final CommandValidator commandValidator;

    private final Map<Request.Command, BiFunction<String, String, Response>> handlers = Map.of(
            Request.Command.CREATE_TASK, this::onCreate,
            Request.Command.CLOSE_TASK, this::onClose,
            Request.Command.REOPEN_TASK, this::onReopen,
            Request.Command.DELETE_TASK, this::onDelete,
            Request.Command.LIST_TASK, this::onList);

    public RequestHandler(UserRepository userRepository, TasksRepository tasksRepository, CommandValidator commandValidator) {
        this.userRepository = userRepository;
        this.tasksRepository = tasksRepository;
        this.commandValidator = commandValidator;
    }

    public Response handleRequest(String requestString) {
        Request request = parseRequest(requestString);
        if(request == null)
            return new Response(Response.Result.WRONG_FORMAT);

        Response validateResult = commandValidator.validate(request);
        if(validateResult != null)
            return validateResult;

        if(!handlers.containsKey(request.getCommand())) {
            LOG.error("Handler matching '" + request.getCommand() + "' command not found");
            return new Response(Response.Result.ERROR);
        }

        return handlers.get(request.getCommand()).apply(request.getUser(), request.getArg());
    }

    /**
     * Parse and validate request
     * @param line incoming request string
     * @return parsed request or null
     */
    private Request parseRequest(String line) {
        if(line == null || line.isBlank()) {
            LOG.error("Incoming request is empty or blank string");
            return null;
        }

        List<String> requestParts = Arrays.asList(line.replaceAll("( +)", " ").trim().split(" "));
        if(requestParts.size() != 3) {
            LOG.error("Incoming request doesn't meet the format requirements");
            return null;
        }

        Optional<Request.Command> command = Request.Command.fromValue(requestParts.get(1));
        if(command.isEmpty()) {
            LOG.error("Command matching string '" + requestParts.get(1) + "'not found");
            return null;
        }

        LOG.info("Request parsed successfully");
        return new Request(requestParts.get(0), command.get(), requestParts.get(2));
    }


    /**
     * Handle CREATE_TASK command
     */
    private Response onCreate(String userName, String taskName) {
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

        return new Response(Response.Result.CREATED);
    }

    /**
     * Handle CLOSE_TASK command
     */
    private Response onClose(String userName, String taskName) {
        // наличие таска проверено на этапе валидации
        Task task = tasksRepository.findFirstByNameAndStateNot(taskName, Task.State.DELETED).get();
        task.setState(Task.State.CLOSED);
        tasksRepository.save(task);
        return new Response(Response.Result.CLOSED);
    }

    /**
     * Handle REOPEN_TASK command
     */
    private Response onReopen(String userName, String taskName) {
        // наличие таска проверено на этапе валидации
        Task task = tasksRepository.findFirstByNameAndStateNot(taskName, Task.State.DELETED).get();
        task.setState(Task.State.CREATED);
        tasksRepository.save(task);
        return new Response(Response.Result.CLOSED);
    }

    /**
     * Handle DELETE_TASK command
     */
    private Response onDelete(String userName, String taskName) {
        // наличие таска проверено на этапе валидации
        Task task = tasksRepository.findFirstByNameAndStateNot(taskName, Task.State.DELETED).get();
        task.setState(Task.State.CREATED);
        tasksRepository.save(task);
        return new Response(Response.Result.CLOSED);
    }

    /**
     * Handle LIST_TASK command
     */
    private Response onList(String userName, String anotherUserName) {
        // наличие пользователя проверено на этапе валидации
        User anotherUser = userRepository.findFirstByName(anotherUserName).get();
        String taskList = tasksRepository.findAllByUserAndStateNot(anotherUser, Task.State.DELETED)
                .stream().map(Object::toString).collect(Collectors.joining(", "));

        return new Response(Response.Result.TASKS, "[" + taskList + "]");
    }
}
