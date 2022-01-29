package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

  @Test
  void check_readme() {
    assertEquals("CREATED", sendMessage("VASYA CREATE_TASK CleanRoom"));
    assertEquals("ACCESS_DENIED", sendMessage("PETYA DELETE_TASK CleanRoom"));
    assertEquals("CREATED", sendMessage("PETYA CREATE_TASK Task1"));
    assertEquals("CREATED", sendMessage("PETYA CREATE_TASK Task2"));
    assertEquals("TASKS [Task1, Task2]", sendMessage("VASYA LIST_TASK PETYA"));
    assertEquals("ERROR", sendMessage("VASYA CREATE_TASK CleanRoom"));
  }
}