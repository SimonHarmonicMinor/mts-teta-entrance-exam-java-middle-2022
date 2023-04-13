package com.example.demo.controller;

import com.example.demo.mappers.Mapper;
import com.example.demo.model.Request;
import com.example.demo.model.Response;
import com.example.demo.model.Result;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TaskRepositoryImpl;

public class TaskControllerImpl implements TaskController {

  private final TaskRepository taskRepository = new TaskRepositoryImpl();

  @Override
  public Response processMessage(String message) {
    Request request = Mapper.mapToRequest(message);
    switch (request.getCommand()) {
      case CREATE_TASK:
        return createTask(request);
      case CLOSE_TASK:
        return closeTask(request);
      case DELETE_TASK:
        return deleteTask(request);
      case REOPEN_TASK:
        return reopenTask(request);
      case LIST_TASK:
        return listTask(request);
      case DELETE_ALL:
        return deleteAll();
      case UNKNOWN:
        return new Response(Result.WRONG_FORMAT);
    }
    return new Response(Result.ERROR);
  }

  private Response createTask(Request request) {
    Result result = taskRepository.createTask(request.getTask(), request.getUser());
    return new Response(result);
  }

  private Response closeTask(Request request) {
    Result result = taskRepository.closeTask(request.getTask(), request.getUser());
    return new Response(result);
  }

  private Response deleteTask(Request request) {
    Result result = taskRepository.deleteTask(request.getTask(), request.getUser());
    return new Response(result);
  }

  private Response reopenTask(Request request) {
    Result result = taskRepository.reopenTask(request.getTask(), request.getUser());
    return new Response(result);
  }

  private Response listTask(Request request) {
    return taskRepository.listTask(request.getUser());
  }

  private Response deleteAll() {
    Result result = taskRepository.deleteAll();
    return new Response(result);
  }
}
