package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

  @Test
  void test_sho() throws Exception {
    String response = sendMessage("VASYA CREATE_TASK CleanRoom");
    assertEquals("CREATED", response);

    resetSocket();
    response = sendMessage("PETYA DELETE_TASK CleanRoom");
    assertEquals("ACCESS_DENIED", response);

    resetSocket();
    response = sendMessage("PETYA CREATE_TASK Task1");
    assertEquals("CREATED", response);

    resetSocket();
    response = sendMessage("PETYA CREATE_TASK Task2");
    assertEquals("CREATED", response);

    resetSocket();
    response = sendMessage("VASYA LIST_TASK PETYA");
    assertEquals("TASKS [Task1, Task2]", response);

    resetSocket();
    response = sendMessage("VASYA CREATE_TASK CleanRoom");
    assertEquals("ERROR", response);
  }
}