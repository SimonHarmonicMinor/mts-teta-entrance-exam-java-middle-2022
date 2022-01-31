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
  void test1() {
    String response = sendMessage("VASYA CREATE_TASK CleanRoom2");
    assertEquals("CREATED", response);
  }

  @Test
  void test2() {
    String response = sendMessage("VASYA CREATE_TASK");
    assertEquals("WRONG_FORMAT", response);
  }

  @Test
  void test3() {
    String response = sendMessage("VASYA CREATESADA_TASK dasdas");
    assertEquals("WRONG_FORMAT", response);
  }
  @Test
  void test4() {
    String response = sendMessage("VASYA DELETE_TASK CleanRoom");
    assertEquals("ERROR", response);
  }
  @Test
  void test5() {
    String response = sendMessage("VASYA DELETE_TASK CleanRoom1");
    assertEquals("ERROR", response);
  }
  @Test
  void test6() {
    String response = sendMessage("GENA DELETE_TASK CleanRoom2");
    assertEquals("ACCESS_DENIED", response);
  }
  @Test
  void test7() {
    String response = sendMessage("GENA DELETE_TASK CleanRoom2");
    assertEquals("ACCESS_DENIED", response);
  }
  @Test
  void test8() {
    String response = sendMessage("VASYA CREATED_TASK");
    assertEquals("WRONG_FORMAT", response);
  }
  @Test
  void test10() {
    String response = sendMessage("VASYA CREATE_TASK CleanRoom2");
    assertEquals("ERROR", response);
  }
}