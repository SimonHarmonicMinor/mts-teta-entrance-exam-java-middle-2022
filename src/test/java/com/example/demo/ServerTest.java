package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

  @Test
  void test() {
    String response = sendMessage("User CREATE_TASK Name");
    assertEquals("CREATED", response);
  }

  @Test
  void test2() {
    String response = sendMessage("User CLOSE_TASK Name");
    assertEquals("ERROR", response);
  }
}