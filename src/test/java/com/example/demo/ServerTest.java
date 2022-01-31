package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

  @Test
  void test() {
    String response = sendMessage("VASYA CREATE_TASK CleanRoom");
    assertEquals("CREATED", response);
  }

  @Test
  void test2() {
    String response = sendMessage("VASYA DELETE_TASK Em");
    assertEquals("DELETED", response);
  }

  @Test
  void test3() {
    String response = sendMessage("Ym LIST_TASK VASYA");
    assertEquals("TASKS[Em, Om]", response);
  }

  @Test
  void test4() {
    String response = sendMessage("Yn CREATE_TASK Em");
    assertEquals("ERROR", response);
  }
  @Test
  void test5() {
    String response = sendMessage("CREATE_TASK Em");
    assertEquals("WRONG_FORMAT", response);
  }
  @Test
  void test6() {
    String response = sendMessage("Yn CREATETASK Em");
    assertEquals("WRONG_FORMAT", response);
  }
  @Test
  void test7() {
    String response = sendMessage("Yn DELETE_TASK Em");
    assertEquals("ACCESS_DENIED", response);
  }
  @Test
  void test8() {
    String response = sendMessage("Yn DELETE_TASK Om");
    assertEquals("ACCESS_DENIED", response);
  }
}