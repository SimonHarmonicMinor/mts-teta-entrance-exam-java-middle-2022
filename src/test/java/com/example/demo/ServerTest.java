package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServerTest extends AbstractServerTest {

  @Test
  void testIncorrectRequest1() {
    String response = sendMessage("VASYA CREATE_TASK Clean Room");
    assertEquals("WRONG_FORMAT", response);
  }

  @Test
  void testIncorrectRequest2() {
    String response = sendMessage("VASYA CREAT_TASK CleanRoom");
    assertEquals("WRONG_FORMAT", response);
  }

  @Test
  @Order(1)
  void testCreateNew() {
    String response = sendMessage("VASYA CREATE_TASK CleanRoom");
    assertEquals("CREATED", response);
  }

  @Test
  @Order(2)
  void testCloseTask() {
    String response = sendMessage("VASYA CLOSE_TASK CleanRoom");
    assertEquals("CLOSED", response);
  }

  @Test
  @Order(3)
  void testReopenTask() {
    String response = sendMessage("VASYA REOPEN_TASK CleanRoom");
    assertEquals("REOPENED", response);
  }

  @Test
  @Order(4)
  void testDeleteNonClosedTask() {
    String response = sendMessage("VASYA DELETE_TASK CleanRoom");
    assertEquals("ERROR", response);
  }

  @Test
  void testDeleteAnothersTask() {
    String response = sendMessage("PETYA DELETE_TASK CleanRoom");
    assertEquals("ACCESS_DENIED", response);
  }

  @Test
  void testListTasksAndDeleteTask() throws Exception {
    String response = sendMessage("PETYA CREATE_TASK Task1");
    assertEquals("CREATED", response);
    afterEach();
    beforeEach();
    response = sendMessage("PETYA CREATE_TASK Task2");
    assertEquals("CREATED", response);
    afterEach();
    beforeEach();
    response = sendMessage("PETYA CREATE_TASK Task3");
    assertEquals("CREATED", response);
    afterEach();
    beforeEach();
    response = sendMessage("PETYA CLOSE_TASK Task3");
    assertEquals("CLOSED", response);
    afterEach();
    beforeEach();
    response = sendMessage("PETYA DELETE_TASK Task3");
    assertEquals("DELETED", response);
    afterEach();
    beforeEach();
    response = sendMessage("VASYA LIST_TASK PETYA");
    assertEquals("TASKS [Task1, Task2]", response);
  }

  @Test
  void testCreateAlreadyCreatedTask() {
    String response = sendMessage("VASYA CREATE_TASK CleanRoom");
    assertEquals("ERROR", response);
  }
}