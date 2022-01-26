package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServerTest extends AbstractServerTest {

  @Test
  @Order(1)
  void test() {
    String response = sendMessage("VASYA CREATE_TASK CleanRoom");
    assertEquals("CREATED", response);
  }

  @Test
  @Order(2)
  void test2() {
    String response = sendMessage("PETYA DELETE_TASK CleanRoom");
    assertEquals("ACCESS_DENIED", response);
  }

  @Test()
  @Order(3)
  void test2_1() {
    String response = sendMessage("PETYA REOPEN_TASK CleanRoom");
    assertEquals("ACCESS_DENIED", response);
  }
  @Test
  @Order(4)
  void test2_2() {
    String response = sendMessage("PETYA LIST_TASK PETYA");
    assertEquals("TASKS", response);
  }

@Test
@Order(5)
  void test3() {
    String response = sendMessage("PETYA CREATE_TASK Task1");
    assertEquals("CREATED", response);
  }
  @Test
  @Order(6)
  void test4() {
    String response = sendMessage("PETYA CREATE_TASK Task2");
    assertEquals("CREATED", response);
  }
  @Test
  @Order(7)
  void test5() {
    String response = sendMessage("VASYA LIST_TASK PETYA");
    assertEquals("TASKS [Task1, Task2]", response);
  }
  @Test
  @Order(8)
  void test6() {
    String response = sendMessage("VASYA CREATE_TASK CleanRoom");
    assertEquals("ERROR", response);
  }
  @Test
  @Order(9)
  void test7() {
    String response = sendMessage("VASYA CLOSE_TASK CleanRoom");
    assertEquals("CLOSED", response);
  }
  @Test
  @Order(10)
  void test8() {
    String response = sendMessage("VASYA REOPEN_TASK CleanRoom");
    assertEquals("REOPENED", response);
  }

  @Test
  @Order(11)
  void test9() {
    String response = sendMessage("VASYA REOPEN_TASK");
    assertEquals("WRONG_FORMAT", response);
  }

  @Test
  @Order(12)
  void test10() {
    String response = sendMessage("");
    assertEquals("WRONG_FORMAT", response);
  }

  @Test
  @Order(13)
  void test11() {
    String response = sendMessage("PETYA REOPEN_TASK CleanRoom");
    assertEquals("ACCESS_DENIED", response);
  }


}