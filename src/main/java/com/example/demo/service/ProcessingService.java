package com.example.demo.service;

import com.example.demo.exception.*;
import com.example.demo.model.Task;
import com.example.demo.model.enums.Response;
import com.example.demo.model.enums.State;
import com.example.demo.model.Request;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

public class ProcessingService {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessingService.class);

    private final TaskRepository taskRepository;
    private final RequestValidator requestValidator;

    public ProcessingService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.requestValidator = new RequestValidator(taskRepository, userRepository);
    }

    public String getResponse(String line) {
        Request request;
        try {
            request = requestValidator.getRequest(line);
            line = process(request);
        } catch (WrongFormatException wfe) {
            LOG.error("Got request with wrong format: {}", wfe.getMessage());
            line = Response.WRONG_FORMAT.name();
        } catch (AccessDeniedException ade) {
            LOG.error("Access denied, request: {}", line);
            line = Response.ACCESS_DENIED.name();
        } catch (ChangeStateException cse) {
            LOG.error("Cannot change state: {}", cse.getMessage());
            line = Response.ERROR.name();
        } catch (TaskAlreadyExistsException taee) {
            LOG.error("Task already exists: {}", taee.getMessage());
            line = Response.ERROR.name();
        } catch (UserNotFoundException enfe) {
            LOG.error("User not found, request: {}", enfe.getMessage());
            line = Response.ERROR.name();
        } catch (WrongCommandException wce) {
            LOG.error("Wrong command: {}", wce.getMessage());
            line = Response.ERROR.name();
        } catch (Exception e) {
            LOG.error("Unexpected exception, request: {}", line);
            line = Response.ERROR.name();
        }
        return line;
    }

    private String process(Request request) {
        String response;

        switch (request.getCommand()) {
            case CREATE_TASK:
                Task task = taskRepository.addTask(request.getTaskName());
                request.getUser().addTask(task);
                response = Response.CREATED.name();
                break;
            case CLOSE_TASK:
                request.getTask().setState(State.CLOSED);
                response = Response.CLOSED.name();
                break;
            case REOPEN_TASK:
                request.getTask().setState(State.CREATED);
                response = Response.REOPENED.name();
                break;
            case DELETE_TASK:
                request.getTask().setState(State.DELETED);
                response = Response.DELETED.name();
                break;
            case LIST_TASK:
                String tasks = request.getListedUser().getTasks().stream()
                        .filter(t -> !State.DELETED.equals(t.getState()))
                        .map(Task::getTitle)
                        .collect(Collectors.joining(", ", "[", "]"));
                response = Response.TASKS.name() + " " + tasks;
                break;
            default:
                response = Response.ERROR.name();
                break;
        }

        return response;
    }
}
