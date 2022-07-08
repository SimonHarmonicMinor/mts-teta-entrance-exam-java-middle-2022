package com.example.demo.service.impl;

import static com.example.demo.common.ResultStatus.*;

import com.example.demo.common.ResultStatus;
import com.example.demo.common.TaskStatus;
import com.example.demo.exception.DeleteTaskException;
import com.example.demo.model.TaskModel;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.TaskService;

public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;

  public TaskServiceImpl(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @Override
  public String save(String name, String user) {
    boolean isCreated = false;
    if (taskRepository.findByName(name) == null) {
      taskRepository.save(new TaskModel(name, user));
      isCreated = true;
    }
    return isCreated ? CREATED.getKey() : ERROR.getKey();
  }

  @Override
  public String delete(String name, String user) {
    TaskModel taskModel = taskRepository.findByName(name);
    if (!user.equals(taskModel.getOwner()))
      return ACCESS_DENIED.getKey();

    if (taskModel.getStatus().equals(TaskStatus.CLOSED)) {
      taskModel.setDeleted(true);
      taskRepository.update(taskModel);
      return DELETED.getKey();
    }
    throw new DeleteTaskException(name, taskModel.getStatus().name());
  }

  @Override
  public String changeStatus(String name, String user, TaskStatus status) {
    TaskModel taskModel = taskRepository.findByName(name);
    if (user.equals(taskModel.getOwner())) {
      if (status.equals(TaskStatus.REOPENED) && taskModel.getStatus().equals(TaskStatus.CREATED)) {
        return ERROR.getKey();
      }
      taskModel.setStatus(status);
      taskRepository.update(taskModel);
      return status.name();
    }
    return ACCESS_DENIED.getKey();
  }

  @Override
  public String findAllByUser(String user) {
    return String.format("%s %s", ResultStatus.TASKS.getKey(),
        taskRepository.findAllByUser(user).toString());
  }

  @Override
  public void deleteAll() {
    taskRepository.deleteAll();
  }
}