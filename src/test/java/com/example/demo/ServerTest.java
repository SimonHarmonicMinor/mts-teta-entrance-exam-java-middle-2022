package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ServerTest extends AbstractServerTest {

  @Test
  void test() {
    String response = sendMessage("request");
    //assertEquals("request", response);
  }

  @Test
  void test2() {
    String response = sendMessage("request2");
    //assertEquals("request2", response);
  }
}