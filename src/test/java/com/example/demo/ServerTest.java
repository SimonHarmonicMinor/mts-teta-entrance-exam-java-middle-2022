package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ServerTest extends AbstractServerTest {
  private static final Logger logger = LoggerFactory.getLogger(ServerTest.class);

  @Test
  void test() {
    String response = sendMessage("request");
    logger.info("Client get response= "+ response);
    assertEquals("request", response);
  }

  @Test
  void test2() {
    String response = sendMessage("request2");
    assertEquals("request2", response);
  }
}