package com.example.demo.repository.impl;

import com.example.demo.model.TaskModel;
import com.example.demo.repository.TaskRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskRepositoryImpl implements TaskRepository {

  private final List<TaskModel> taskModels = new ArrayList<>();

  @Override
  public void save(TaskModel taskModel) {
    taskModel.setIndex(taskModels.size());
    taskModels.add(taskModel);
  }

  @Override
  public void update(TaskModel taskModel) {
    taskModels.set(taskModel.getIndex(), taskModel);
  }

  @Override
  public List<String> findAllByUser(String user) {
    return taskModels
        .stream().filter(task -> task.getOwner().equals(user) && task.isDeleted())
        .map(TaskModel::getName).collect(Collectors.toList());
  }

  @Override
  public TaskModel findByName(String name) {
    return taskModels
        .stream().filter(task -> task.isDeleted() && task.getName().equals(name)).findFirst()
        .orElse(null);
  }

  @Override
  public void deleteAll() {
    taskModels.clear();
  }
}
