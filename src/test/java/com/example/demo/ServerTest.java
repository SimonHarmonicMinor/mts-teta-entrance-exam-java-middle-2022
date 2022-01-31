package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest extends AbstractServerTest {

  @Test
  void test() {
    String response = sendMessage("VASYA CREATE_TASK CleanRoom");
    assertEquals("CREATED", response);
  }

  @Test
  void test2() {
    String response = sendMessage("PETYA DELETE_TASK CleanRoom");
    assertEquals("ACCESS_DENIED", response);
  }

  @Test
  void test3() {
    String response = sendMessage("PETYA CREATE_TASK Task1");
    assertEquals("CREATED", response);
  }

  @Test
  void test4() {
    String response = sendMessage("PETYA CREATE_TASK Task2");
    assertEquals("CREATED", response);
  }

  @Test
  void test5() {
    String response = sendMessage("VASYA LIST_TASK PETYA");
    assertEquals("TASKS [Task1, Task2]", response);
  }

  @Test
  void test6() {
    String response = sendMessage("VASYA CREATE_TASK CleanRoom");
    assertEquals("ERROR", response);
  }

}