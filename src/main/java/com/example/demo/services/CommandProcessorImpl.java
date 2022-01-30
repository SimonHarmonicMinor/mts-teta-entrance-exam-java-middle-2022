package com.example.demo.services;

import com.example.demo.models.*;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.security.Security;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.models.Result.*;

public class CommandProcessorImpl implements CommandProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(CommandProcessorImpl.class);

    private final TaskRepository taskRepository;
    private final Security security;

    public CommandProcessorImpl(TaskRepository taskRepository, Security security) {
        this.taskRepository = taskRepository;
        this.security = security;
    }

    @Override
    public Response process(Command command, User user, String arg) {
        LOG.debug("Command: {}\tUser: {}\tArgument: {}", command, user.getName(), arg);
        switch (command) {
            case CREATE_TASK:
                return create(user, arg);
            case LIST_TASK:
                return list(arg);
            default:
                return accessCommand(command, user, arg);
        }
    }

    private Response create(User user, String arg) {
        Optional<Task> taskOptional = taskRepository.findByName(arg);

        if (taskOptional.isPresent()) {
            LOG.error("Task exists");
            return Response.error();
        }
        taskRepository.create(new Task(arg, user));
        LOG.info("Task created");
        return new Response(CREATED);
    }

    private Response accessCommand(Command command, User user, String arg) {
        Optional<Task> taskOptional = taskRepository.findByName(arg);
        if (taskOptional.isEmpty()) {
            LOG.error("Task: {}, not found", arg);
            return Response.error();
        }
        Task task = taskOptional.get();

        boolean ok = security.checkAccess(user, task);

        if (ok) {
            switch (command) {
                case DELETE_TASK:
                    return delete(task);
                case CLOSE_TASK:
                    return close(task);
                case REOPEN_TASK:
                    return reopen(task);
            }
        }
        return new Response(ACCESS_DENIED);
    }

    private Response delete(Task task) {
        if (TaskStatus.CLOSED == task.getStatus()) {
            LOG.info("Task: {} deleted", task.getName());
            task.setStatus(TaskStatus.DELETED);
            taskRepository.updateStatus(task);
            return new Response(DELETED);
        }
        return Response.error();
    }

    private Response close(Task task) {
        if (TaskStatus.CREATED == task.getStatus()) {
            LOG.info("Task: {} closed", task.getName());
            task.setStatus(TaskStatus.CLOSED);
            taskRepository.updateStatus(task);
            return new Response(CLOSED);
        }
        return Response.error();
    }

    private Response reopen(Task task) {
        if (TaskStatus.CLOSED == task.getStatus()) {
            LOG.info("Task: {} reopened", task.getName());
            task.setStatus(TaskStatus.CREATED);
            taskRepository.updateStatus(task);
            return new Response(REOPENED);
        }
        return Response.error();
    }

    private Response list(String arg) {
        List<String> tasks = taskRepository.findAllNoDeletedByUserName(arg)
                .stream()
                .map(Task::getName)
                .collect(Collectors.toList());
        LOG.info("Tasks found");
        return new Response(TASKS, tasks);
    }
}
