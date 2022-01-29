package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

class ServerTest extends AbstractServerTest {
  /**
   * Пользователь может добавлять, удалять, закрывать и заново открывать задачи
   */
  @Test
  void testCrud() {
    assertTaskOperation(Operation.CREATE_TASK, User.VASYA, Task.Task1, Response.CREATED);
    assertTaskOperation(Operation.CLOSE_TASK, User.VASYA, Task.Task1, Response.CLOSED);
    assertTaskOperation(Operation.REOPEN_TASK, User.VASYA, Task.Task1, Response.REOPENED);
    assertTaskOperation(Operation.DELETE_TASK, User.VASYA, Task.Task1, Response.DELETED);
  }

  /**
   * Названия задач должны быть уникальными для всех пользователей (удаленные не учитываются).
   */
  @Test
  void testTaskDup() {
    sendTaskOperation(Operation.CREATE_TASK, User.VASYA, Task.Task1);
    assertTaskOperation(Operation.CREATE_TASK, User.PETYA, Task.Task1, Response.ERROR);

    sendTaskOperation(Operation.CLOSE_TASK, User.VASYA, Task.Task1);
    assertTaskOperation(Operation.CREATE_TASK, User.VASYA, Task.Task1, Response.ERROR);
  }

  /**
   * Пользователь может получить список всех задач любого другого пользователя, кроме удаленных.
   */
  @Test
  void testTaskListOk() {
    sendTaskOperation(Operation.CREATE_TASK, User.VASYA, Task.Task1);

    sendTaskOperation(Operation.CREATE_TASK, User.VASYA, Task.Task2);
    sendTaskOperation(Operation.CLOSE_TASK, User.VASYA, Task.Task2);
    sendTaskOperation(Operation.DELETE_TASK, User.VASYA, Task.Task2);

    sendTaskOperation(Operation.CREATE_TASK, User.VASYA, Task.Task3);
    sendTaskOperation(Operation.CLOSE_TASK, User.VASYA, Task.Task3);

    assertTaskList(User.PETYA, new Task[] {Task.Task1, Task.Task3});
  }

  /**
   * Пользователь может закрывать, удалять и заново открывать только свои задачи
   */
  @Test
  void testDeletion() {
    sendTaskOperation(Operation.CREATE_TASK, User.VASYA, Task.Task1);
    assertTaskOperation(Operation.CLOSE_TASK, User.PETYA, Task.Task1, Response.ACCESS_DENIED);

    sendTaskOperation(Operation.CLOSE_TASK, User.VASYA, Task.Task1);
    assertTaskOperation(Operation.DELETE_TASK, User.PETYA, Task.Task1, Response.ACCESS_DENIED);
  }

  /**
   * Задача проходит следующие состояния: CREATED <--> CLOSED -> DELETED.
   * При этом задача в статусе CREATED не может сразу перейти в DELETED.
   */
  @Test
  void testTaskLifecycle() {
    assertTaskOperation(Operation.CLOSE_TASK, User.VASYA, Task.Task1, Response.ERROR);

    sendTaskOperation(Operation.CREATE_TASK, User.VASYA, Task.Task2);
    assertTaskOperation(Operation.DELETE_TASK, User.VASYA, Task.Task2, Response.ERROR);
  }

  private static String buildListResponse(Task[] items) {
    return Arrays
            .stream(items)
            .map(Enum::name)
            .collect(Collectors.joining(", ", "[", "]"));
  }

  private void assertTaskList(User user, Task[] tasks) {
    var actual = sendMessage(Operation.LIST_TASK + " " + user);
    assertEquals(buildListResponse(tasks), actual);
  }

  private String sendTaskOperation(Operation operation, User user, Task task) {
    return sendMessage(user + " " + operation + " " + task);
  }

  private void assertTaskOperation(Operation operation, User user, Task task, Response expected) {
    var actualResponse = sendTaskOperation(operation, user, task);
    assertEquals(expected, actualResponse);
  }

  enum Response {
    CREATED,
    DELETED,
    CLOSED,
    REOPENED,
    WRONG_FORMAT,
    ACCESS_DENIED,
    ERROR
  }

  enum Task {
    Task1,
    Task2,
    Task3
  }

  enum Operation {
    CREATE_TASK,
    DELETE_TASK,
    CLOSE_TASK,
    REOPEN_TASK,
    LIST_TASK
  }

  enum User {
    PETYA,
    VASYA
  }

}