package com.example.demo.repository;

import com.example.demo.model.*;

import java.util.*;

public class TaskRepositoryImpl implements TaskRepository {

  private final Map<User, ArrayList<Task>> userTasks = new HashMap<>();

  private final Set<Task> allTask = new HashSet<>();

  @Override
  public Result createTask(Task task, User user) {
    if (!allTask.contains(task)) {
      task.setStatus(TaskStatus.CREATED);
      allTask.add(task);
      ArrayList<Task> tasks = userTasks.get(user);
      if (tasks != null) {
        tasks.add(task);
      } else {
        userTasks.put(user, new ArrayList<>(List.of(task)));
      }
      return Result.CREATED;
    } else {
      return Result.ERROR;
    }
  }

  @Override
  public Result closeTask(Task task, User user) {
    if (allTask.contains(task)) {
      List<Task> tasks = userTasks.get(user);
      if (tasks != null) {
        Task changedTask = tasks
            .stream()
            .filter(val -> val.equals(task) && val.getStatus() == TaskStatus.CREATED)
            .peek(val -> val.setStatus(TaskStatus.CLOSED))
            .findFirst()
            .orElse(null);
        if (changedTask != null) {
          return Result.CLOSED;
        } else {
          return Result.ERROR;
        }
      } else {
        return Result.ACCESS_DENIED;
      }
    } else {
      return Result.ERROR;
    }
  }

  @Override
  public Result deleteTask(Task task, User user) {
    if (task == null) {
      return Result.WRONG_FORMAT;
    }
    if (allTask.contains(task)) {
      List<Task> tasks = userTasks.get(user);
      if (tasks != null) {
        Task deletedTask = tasks
            .stream()
            .filter(val -> val.equals(task) && val.getStatus() == TaskStatus.CLOSED)
            .peek(val -> val.setStatus(TaskStatus.DELETED))
            .findFirst()
            .orElse(null);
        if (deletedTask != null) {
          tasks.remove(task);
          allTask.remove(task);
          return Result.DELETED;
        } else {
          return Result.ERROR;
        }
      } else {
        return Result.ACCESS_DENIED;
      }
    } else {
      return Result.ERROR;
    }
  }

  @Override
  public Result reopenTask(Task task, User user) {
    if (allTask.contains(task)) {
      List<Task> tasks = userTasks.get(user);
      if (tasks != null) {
        Task reopenedTask = tasks
            .stream()
            .filter(val -> val.equals(task) && val.getStatus() == TaskStatus.CLOSED)
            .peek(val -> val.setStatus(TaskStatus.CREATED))
            .findFirst()
            .orElse(null);
        if (reopenedTask != null) {
          return Result.REOPENED;
        } else {
          return Result.ERROR;
        }
      } else {
        return Result.ACCESS_DENIED;
      }
    } else {
      return Result.ERROR;
    }
  }

  @Override
  public Response listTask(User user) {
    if (user == null) {
      return new Response(Result.WRONG_FORMAT);
    }
    ArrayList<Task> tasks = userTasks.get(user);
    if (tasks != null) {
      return new Response(
          Result.TASKS,
          Arrays.toString(tasks.stream().filter(val -> val.getStatus() != TaskStatus.DELETED).map(Task::getName).toArray())
      );
    } else {
      return new Response(Result.ERROR);
    }
  }

  @Override
  public Result deleteAll() {
    userTasks.clear();
    allTask.clear();
    return Result.DELETED;
  }
}
