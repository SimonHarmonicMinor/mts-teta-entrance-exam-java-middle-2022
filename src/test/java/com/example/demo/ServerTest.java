package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

  @Test
  void test001() {
    String response = sendMessage("WRONG_FORMAT");
    assertEquals("WRONG_FORMAT", response);

  }

  @Test
  void test002() {
    String response = sendMessage("user Create_TASK task1");
    assertEquals("WRONG_FORMAT", response);
  }

  @Test
  void test003() {
    String response = sendMessage("user1 CREATE_TASK task1");
    assertEquals("CREATED", response);
  }

  @Test
  void test004() {
    String response = sendMessage("user2 CREATE_TASK task2");
    assertEquals("CREATED", response);
  }

  @Test
  void test005() {
    String response = sendMessage("user2 CREATE_TASK task2");
    assertEquals("ERROR", response);
  }

  @Test
  void test006() {
    String response = sendMessage("user1 CLOSE_TASK task2");
    assertEquals("ACCESS_DENIED", response);
  }

  @Test
  void test007() {
    String response = sendMessage("user1 CLOSE_TASK task1");
    assertEquals("CLOSED", response);
  }

  @Test
  void test008() {
    String response = sendMessage("user1 REOPEN_TASK task2");
    assertEquals("ACCESS_DENIED", response);
  }

  @Test
  void test009() {
    String response = sendMessage("user1 REOPEN_TASK task1");
    assertEquals("REOPENED", response);
  }

  @Test
  void test010() {
    String response = sendMessage("user1 CLOSE_TASK task1");
    assertEquals("CLOSED", response);
  }

  @Test
  void test011() {
    String response = sendMessage("user1 DELETE_TASK task1");
    assertEquals("DELETED", response);
  }

  @Test
  void test012() {
    String response = sendMessage("user1 CREATE_TASK task5");
    assertEquals("CREATED", response);
  }

  @Test
  void test013() {
    String response = sendMessage("user1 LIST_TASK user6");
    assertEquals("TASKS []", response);
  }

  @Test
  void test014() {
    String response = sendMessage("user1 LIST_TASK user1");
    assertEquals("TASKS [task5]", response);
  }

  @Test
  void test015() {
    String response = sendMessage("user1 LIST_TASK user");
    assertEquals("TASKS []", response);
  }

  @Test
  void test016() {
    String response = sendMessage("user2 CREATE_TASK 123");
    assertEquals("CREATED", response);
  }

  @Test
  void test017() {
    String response = sendMessage("user2 CREATE_TASK 1234");
    assertEquals("CREATED", response);
  }

  @Test
  void test018() {
    String response = sendMessage("user2 CREATE_TASK 12345");
    assertEquals("CREATED", response);
  }

  @Test
  void test019() {
    String response = sendMessage("user1 LIST_TASK user2");
    assertEquals("TASKS [task2, 123, 1234, 12345]", response);
  }
}