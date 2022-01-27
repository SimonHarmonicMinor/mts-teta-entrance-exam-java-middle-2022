package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

  @Test
  void test() {
    String response = sendMessage("user1 CREATE_TASK task1");
    assertEquals("CREATED", response);
  }

  @Test
  void test2() {
    String response = sendMessage("user1 CREATE_TASK task1");
    assertEquals("ERROR", response);
  }
}