package com.example.demo.repository;

import com.example.demo.model.Response;
import com.example.demo.model.Result;
import com.example.demo.model.Task;
import com.example.demo.model.User;

public interface TaskRepository {

  Result createTask(Task task, User user);


  Result closeTask(Task task, User user);

  Result deleteTask(Task task, User user);

  Result reopenTask(Task task, User user);

  Response listTask(User user);

  Result deleteAll();
}
