package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.UUID;

class ServerTest extends AbstractServerTest {

  @Test()
  void testCreateTaskWrongFormatError() {
    assertEquals("WRONG_FORMAT", sendMessage("VASYA"));
  }

  @Test
  void testCreateTaskWrongFormatError2() {
    assertEquals("WRONG_FORMAT", sendMessage("VASYA CREATE_TASK"));
  }

  @Test
  void testCreateTaskWrongFormatError3() {
    assertEquals("WRONG_FORMAT", sendMessage("VASYA REMOVE_TASK TASK"));
  }

  @Test
  void testCreateTaskWrongFormatError4() {
    assertEquals("WRONG_FORMAT", sendMessage(""));
  }

  @Test
  void testCreateTaskWrongFormatError5() {
    assertEquals("WRONG_FORMAT", sendMessage("VASYA CREATE_TASK New Task"));
  }

  @Test
  void testCreateTaskSuccess() {
    assertEquals("CREATED", sendMessage("VASYA CREATE_TASK CleanRoom"));
  }

  @Test
  void testCreateTaskExistingNameSameUserError() {
    assertEquals("CREATED", sendMessage("USER2 CREATE_TASK Task3"));
    assertEquals("ERROR", sendMessage("USER2 CREATE_TASK Task3"));
  }

  @Test
  void testCreateTaskExistingNameOtherUserError() {
    String taskName = UUID.randomUUID().toString();
    assertEquals("CREATED", sendMessage("USER3 CREATE_TASK " + taskName));
    assertEquals("ERROR", sendMessage("USER4 CREATE_TASK " + taskName));
  }

  @Test
  void testCreateTaskExistingNameUpperCaseSuccess() {
    String taskName = "NewTask";
    assertEquals("CREATED", sendMessage("USER5 CREATE_TASK " + taskName));
    assertEquals("CREATED", sendMessage("USER5 CREATE_TASK " + taskName.toUpperCase()));
  }

  @Test
  void testCreateWithDeletedName() {
    String taskName = UUID.randomUUID().toString();
    assertEquals("CREATED", sendMessage("Pushkin CREATE_TASK " + taskName));
    assertEquals("CLOSED", sendMessage("Pushkin CLOSE_TASK " + taskName));
    assertEquals("DELETED", sendMessage("Pushkin DELETE_TASK " + taskName));
    assertEquals("CREATED", sendMessage("Pushkin CREATE_TASK " + taskName));
  }

  @Test
  void testCloseTaskSuccess() {
    assertEquals("CREATED", sendMessage("U CREATE_TASK T"));
    assertEquals("CLOSED", sendMessage("U CLOSE_TASK T"));
  }

  @Test
  void testCloseTaskNotExistError() {
    assertEquals("ACCESS_DENIED", sendMessage("U CLOSE_TASK T2"));
  }

  @Test
  void testCloseTaskDeletedError() {
    assertEquals("CREATED", sendMessage("U CREATE_TASK T3"));
    assertEquals("CLOSED", sendMessage("U CLOSE_TASK T3"));
    assertEquals("DELETED", sendMessage("U DELETE_TASK T3"));
    assertEquals("ACCESS_DENIED", sendMessage("U CLOSE_TASK T3"));
  }

  @Test
  void testCloseTaskLowerCaseAccessDenied() {
    String userName = "User_1";
    assertEquals("CREATED", sendMessage(userName + " CREATE_TASK TODO"));
    assertEquals("ACCESS_DENIED", sendMessage(userName.toLowerCase() + " CLOSE_TASK TODO"));
  }

  @Test
  void testCloseReopenedTaskSuccess() {
    assertEquals("CREATED", sendMessage("U CREATE_TASK -"));
    assertEquals("CLOSED", sendMessage("U CLOSE_TASK -"));
    assertEquals("REOPENED", sendMessage("U REOPEN_TASK -"));
    assertEquals("CLOSED", sendMessage("U CLOSE_TASK -"));
  }

  @Test
  void testReopenTaskSuccess() {
    assertEquals("CREATED", sendMessage("U CREATE_TASK U"));
    assertEquals("CLOSED", sendMessage("U CLOSE_TASK U"));
    assertEquals("REOPENED", sendMessage("U REOPEN_TASK U"));
  }

  @Test
  void testReopenCreatedError() {
    assertEquals("CREATED", sendMessage("UFO CREATE_TASK UFO/TASK"));
    assertEquals("ERROR", sendMessage("UFO REOPEN_TASK UFO/TASK"));
  }

  @Test
  void testReopenDeletedTaskError() {
    String userName = UUID.randomUUID().toString();
    String taskName = UUID.randomUUID().toString();
    assertEquals("CREATED", sendMessage(userName + " CREATE_TASK " + taskName));
    assertEquals("CLOSED", sendMessage(userName + " CLOSE_TASK " + taskName));
    assertEquals("DELETED", sendMessage(userName + " DELETE_TASK " + taskName));
    assertEquals("ACCESS_DENIED", sendMessage(userName + " REOPEN_TASK " + taskName));
  }

  @Test
  void testReopenTaskAccessDenied() {
    assertEquals("CREATED", sendMessage("0 CREATE_TASK Задача"));
    assertEquals("CLOSED", sendMessage("0 CLOSE_TASK Задача"));
    assertEquals("ACCESS_DENIED", sendMessage("00 REOPEN_TASK Задача"));
  }

  @Test
  void testDeleteTaskSuccess() {
    assertEquals("CREATED", sendMessage("KOLYA CREATE_TASK TheTask"));
    assertEquals("TASKS [TheTask]", sendMessage("KOLYA LIST_TASK KOLYA"));

    assertEquals("CLOSED", sendMessage("KOLYA CLOSE_TASK TheTask"));
    assertEquals("TASKS [TheTask]", sendMessage("KOLYA LIST_TASK KOLYA"));

    assertEquals("DELETED", sendMessage("KOLYA DELETE_TASK TheTask"));
    assertEquals("TASKS []", sendMessage("KOLYA LIST_TASK KOLYA"));
  }

  @Test
  void testDeleteTaskNotClosedError() {
    assertEquals("CREATED", sendMessage("USER CREATE_TASK T4"));
    assertEquals("ERROR", sendMessage("USER DELETE_TASK T4"));
  }

  @Test
  void testDeleteReopenedTaskError() {
    assertEquals("CREATED", sendMessage("U CREATE_TASK 17"));
    assertEquals("CLOSED", sendMessage("U CLOSE_TASK 17"));
    assertEquals("REOPENED", sendMessage("U REOPEN_TASK 17"));
    assertEquals("ERROR", sendMessage("U DELETE_TASK 17"));
  }

  @Test
  void testDeleteTaskAccessDenied() {
    sendMessage("MASHA CREATE_TASK CleanRoom");
    assertEquals("ACCESS_DENIED", sendMessage("MASHA DELETE_TASK CleanRoom"));
  }

  @Test
  void testGetMultipleTasksSuccess() {
    sendMessage("PETYA CREATE_TASK Task1");
    sendMessage("PETYA CREATE_TASK Task11");
    sendMessage("PETYA CREATE_TASK Task21");
    assertEquals("TASKS [Task1, Task11, Task21]", sendMessage("VASYA LIST_TASK PETYA"));
  }

  @Test
  void testGetEmptyTaskListSuccess() {
    assertEquals("TASKS []", sendMessage("NOBODY LIST_TASK NOBODY"));
  }

}