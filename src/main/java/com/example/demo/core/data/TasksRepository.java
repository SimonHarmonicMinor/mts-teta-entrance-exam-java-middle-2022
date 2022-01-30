package com.example.demo.core.data;

import com.example.demo.core.enums.Status;

import java.util.ArrayList;
import java.util.List;

public class TasksRepository {

  private static final List<Task> store = new ArrayList<>();

  public void add(Task task) {
    store.add(task);
  }

  public boolean isExist(String taskName) {
    for (Task task : store) {
      if (task.getName().equals(taskName) && task.getStatus() != Status.DELETED) {
        return true;
      }
    }
    return false;
  }

  public Task findByName(String name) {
    for (Task task : store) {
      if (task.getName().equals(name) && task.getStatus() != Status.DELETED) {
        return task;
      }
    }
    return null;
  }

  public List<Task> findByOwner(String owner) {
    List<Task> tasks = new ArrayList<>();
    for (Task task : store) {
      if (task.getOwner().equals(owner) && task.getStatus() != Status.DELETED) {
        tasks.add(task);
      }
    }
    return tasks;
  }

  public void delete(String taskName, String owner) {
    for (Task task : store) {
      if (task.getName().equals(taskName) && task.getOwner().equals(owner)) {
        task.setStatus(Status.DELETED);
      }
    }
  }
}
