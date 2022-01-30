package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

  @Test
  void test1() {
    assertEquals("CREATED", sendMessage("Dima CREATE_TASK cleanRoom"));
  }

  @Test
  void test2() {
    assertEquals("ACCESS_DENIED", sendMessage("Irina DELETE_TASK cleanRoom"));
  }

  @Test
  void test3() {
    assertEquals("CREATED", sendMessage("Irina CREATE_TASK cleanEverything"));
  }

  @Test
  void test4() {
    assertEquals("CREATED", sendMessage("Irina CREATE_TASK eatEverything"));
  }

  @Test
  void test5() {
    assertEquals("TASKS [cleanEverything, eatEverything]", sendMessage("Dima LIST_TASK Irina"));
  }

  @Test
  void test6() {
    assertEquals("ERROR", sendMessage("Dima CREATE_TASK cleanRoom"));
  }

  @Test
  void test7() {
    assertEquals("CREATED", sendMessage("Irina CREATE_TASK sleep"));
  }

  @Test
  void test8() {
    assertEquals("CLOSED", sendMessage("Irina CLOSE_TASK cleanEverything"));
  }

  @Test
  void test9() {
    assertEquals("TASKS [eatEverything, sleep]", sendMessage("Dima LIST_TASK Irina"));
  }
}