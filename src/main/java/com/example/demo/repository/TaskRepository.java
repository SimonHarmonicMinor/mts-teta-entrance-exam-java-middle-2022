package com.example.demo.repository;

import com.example.demo.model.TaskModel;
import java.util.List;

public interface TaskRepository {

  void save(TaskModel taskModel);

  void update(TaskModel taskModel);

  List<String> findAllByUser(String user);

  TaskModel findByName(String name);

  void deleteAll();

}