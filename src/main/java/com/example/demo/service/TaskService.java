package com.example.demo.service;

import com.example.demo.common.TaskStatus;

public interface TaskService {

  String save(String name, String user);

  String delete(String name, String user);

  String changeStatus(String name, String user, TaskStatus status);

  String findAllByUser(String user);

  void deleteAll();

}
