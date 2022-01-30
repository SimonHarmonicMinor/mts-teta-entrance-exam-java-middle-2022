package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServerTest extends AbstractServerTest {

  @Test
  void test01() {
    String response = sendMessage("VASYA CREATE_TASK CleanRoom");
    assertEquals("CREATED", response);
  }

  @Test
  void test02() {
    String response = sendMessage("PETYA DELETE_TASK CleanRoom");
    assertEquals("ACCESS_DENIED", response);
  }

  @Test
  void test03() {
    String response = sendMessage("PETYA CREATE_TASK Task1");
    assertEquals("CREATED", response);
  }

  @Test
  void test04() {
    String response = sendMessage("PETYA CREATE_TASK Task2");
    assertEquals("CREATED", response);
  }

  @Test
  void test05() {
    String response = sendMessage("VASYA LIST_TASK PETYA");
    List<String> expectedTitlesList = Arrays.asList("TASKS [Task1, Task2]", "TASKS [Task2, Task1]");
    assertTrue(expectedTitlesList.contains((response)));
  }

  @Test
  void test06() {
    String response = sendMessage("VASYA CREATE_TASK CleanRoom");
    assertEquals("ERROR", response);
  }

  @Test
  void test07() {
    String response = sendMessage("PETYA DELETE_TASK Task1");
    assertEquals("ERROR", response);
  }

  @Test
  void test08() {
    String response = sendMessage("VASYA LIST_TASK PETYA");
    List<String> expectedTitlesList = Arrays.asList("TASKS [Task1, Task2]", "TASKS [Task2, Task1]");
    assertTrue(expectedTitlesList.contains((response)));
  }

  @Test
  void test09() {
    String response = sendMessage("PETYA CLOSE_TASK Task2");
    assertEquals("CLOSED", response);
  }

  @Test
  void test10() {
    String response = sendMessage("PETYA REOPEN_TASK Task2");
    assertEquals("REOPENED", response);
  }

  @Test
  void test11() {
    String response = sendMessage("PETYA DELETE_TASK Task2");
    assertEquals("ERROR", response);
  }

  @Test
  void test12() {
    String response = sendMessage("PETYA CLOSE_TASK Task2");
    assertEquals("CLOSED", response);
  }

  @Test
  void test13() {
    String response = sendMessage("PETYA DELETE_TASK Task2");
    assertEquals("DELETED", response);
  }

  @Test
  void test14() {
    String response = sendMessage("PETYA REOPEN_TASK Task2");
    assertEquals("ERROR", response);
  }

  @Test
  void test15() {
    String response = sendMessage("PETYA REOPENTASK Task2");
    assertEquals("WRONG_FORMAT", response);
  }

  @Test
  void test16() {
    String response = sendMessage("PETYA REOPENTASK Task2 AAAA");
    assertEquals("WRONG_FORMAT", response);
  }
}