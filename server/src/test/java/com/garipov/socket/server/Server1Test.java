package com.garipov.socket.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Server1Test extends AbstractServer1Test {

  @Test
  void test() {
    String response = sendMessage("request");
    assertEquals("request", response);
  }

  @Test
  void test2() {
    String response = sendMessage("request2");
    assertEquals("request2", response);
  }
}