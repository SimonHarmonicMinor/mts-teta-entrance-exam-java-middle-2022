package com.example.demo;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static com.example.demo.enums.ServerResponse.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Для упрощения предлагается сквозной порядок тестирования,
 * когда тесты должны выполняться друг за другом
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServerTest extends AbstractServerTest {

  @Test
  @Order(1)
  void createTaskTest() {
    String response = sendMessage("VASYA CREATE_TASK CleanRoom");
    assertEquals(CREATED.name(), response);
  }

  @Test
  @Order(2)
  void invalidCreateTaskTest() {
    String response = sendMessage("SOMEBODY CREATE_TASK CleanRoom");
    assertEquals(ERROR.name(), response);
  }

  @Test
  @Order(3)
  void normalCloseTaskTest() {
    sendMessage("SOMEBODY CREATE_TASK TaskA");

    String response = sendMessage("SOMEBODY CLOSE_TASK TaskA");
    assertEquals(CLOSED.name(), response);
  }

  @Test
  @Order(4)
  void normalReopenTaskTest() {
    String response = sendMessage("SOMEBODY REOPEN_TASK TaskA");
    assertEquals(REOPENED.name(), response);
  }

  @Test
  @Order(5)
  void normalDeleteTaskTest() {
    sendMessage("SOMEBODY CLOSE_TASK TaskA");

    String response = sendMessage("SOMEBODY DELETE_TASK TaskA");
    assertEquals(DELETED.name(), response);
  }

  @Test
  @Order(6)
  /**
   * Получаем список всех задач другого пользователя, кроме удаленных
   */
  void listTaskTest() {
    sendMessage("PETYA CREATE_TASK Task1");
    sendMessage("PETYA CREATE_TASK Task2");
    sendMessage("PETYA CREATE_TASK Task3");
    sendMessage("PETYA CLOSE_TASK Task3");
    sendMessage("PETYA DELETE_TASK Task3");

    String response = sendMessage("VASYA LIST_TASK PETYA");
    assertEquals(TASKS.name() + " [Task1, Task2]", response);
  }

  @Test
  @Order(7)
  void invalidAccessDeleteTaskTest() {
    String response = sendMessage("VASYA DELETE_TASK Task1");
    assertEquals(ACCESS_DENIED.name(), response);
  }

  @Test
  @Order(8)
  void invalidAccessReopenTaskTest() {
    String response = sendMessage("VASYA REOPEN_TASK Task1");
    assertEquals(ACCESS_DENIED.name(), response);
  }

  @Test
  @Order(9)
  void invalidAccessCloseTaskTest() {
    String response = sendMessage("VASYA CLOSE_TASK Task1");
    assertEquals(ACCESS_DENIED.name(), response);
  }

  @Test
  @Order(10)
  void emptyListTaskTest() {
    String response = sendMessage("PETYA LIST_TASK SOMEBODY");
    assertEquals(TASKS.name() + " []", response);
  }

  @Test
  @Order(11)
  void createTaskWithSameNameAsDeletedOneTest() {
    sendMessage("GLEB CREATE_TASK TaskB");
    sendMessage("GLEB CLOSE_TASK TaskB");
    sendMessage("GLEB DELETE_TASK TaskB");

    String response = sendMessage("PETYA CREATE_TASK TaskB");
    assertEquals(CREATED.name(), response);
  }

  @Test
  @Order(12)
  void invalidTaskChangeStatusTests() {
    String response;

    // CREATED -> DELETED
    sendMessage("ED CREATE_TASK TaskC");
    response = sendMessage("ED DELETE_TASK TaskC");

    assertEquals(ERROR.name(), response);

    // CREATED -> REOPENED
    response = sendMessage("ED REOPEN_TASK TaskC");

    assertEquals(ERROR.name(), response);

    // DELETED -> REOPENED
    sendMessage("ED CLOSE_TASK TaskC");
    sendMessage("ED DELETE_TASK TaskC");
    response = sendMessage("ED REOPEN_TASK TaskC");

    assertEquals(ERROR.name(), response);
  }

  @Test
  @Order(13)
  void wrongCommandFormatTests() {
    String response;

    // case1
    response = sendMessage("ED DELETE_TASK");
    assertEquals(WRONG_FORMAT.name(), response);

    // case2
    response = sendMessage("REOPEN_TASK ED TaskC");
    assertEquals(WRONG_FORMAT.name(), response);

    // case3
    response = sendMessage("ED REOPEN_TASK TaskC TaskD");
    assertEquals(WRONG_FORMAT.name(), response);

    // case4
    response = sendMessage("ED REOPEN_TASK DELETE_TASK TaskC");
    assertEquals(WRONG_FORMAT.name(), response);

    // case5
    response = sendMessage("ED create_task TaskD");
    assertEquals(WRONG_FORMAT.name(), response);
  }

}