package com.example.demo.utils;

import static com.example.demo.common.TaskCommand.__DELETE_ALL;
import static com.example.demo.common.TaskStatus.CLOSED;
import static com.example.demo.common.TaskStatus.REOPENED;

import com.example.demo.common.ResultStatus;
import com.example.demo.common.TaskCommand;
import com.example.demo.exception.DeleteTaskException;
import com.example.demo.repository.impl.TaskRepositoryImpl;
import com.example.demo.service.TaskService;
import com.example.demo.service.impl.TaskServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouterService {
  private static final Logger LOG = LoggerFactory.getLogger(RouterService.class);

  private static RouterService instance;
  private final TaskService taskService;
  private String request;

  private RouterService(TaskService taskService) {
    this.taskService = taskService;
  }

  public static RouterService getInstance() {
    if(instance == null)
      instance = new RouterService(new TaskServiceImpl(new TaskRepositoryImpl()));
    return instance;
  }

  public RouterService init(String request) {
    this.request = request;
    return this;
  }

  public String route() {
    if (request.equals(__DELETE_ALL.name())) {
      taskService.deleteAll();
      return request;
    }
    try {
      String[] splitResponse = request.split(" ");

      String user = splitResponse[0];
      TaskCommand command = TaskCommand.valueOf(splitResponse[1]);
      String arg = splitResponse[2];

      switch (command) {
        case CREATE_TASK: return taskService.save(arg, user);
        case CLOSE_TASK: return taskService.changeStatus(arg, user, CLOSED);
        case REOPEN_TASK: return taskService.changeStatus(arg, user, REOPENED);
        case DELETE_TASK: return taskService.delete(arg, user);
        case LIST_TASK: return taskService.findAllByUser(user);
        default: return ResultStatus.WRONG_FORMAT.getKey();
      }
    }catch (DeleteTaskException deleteTaskException) {
      return ResultStatus.ERROR.getKey();
    } catch (Exception e) {
      LOG.error(e.getMessage());
      return ResultStatus.WRONG_FORMAT.getKey();
    }
  }
}
