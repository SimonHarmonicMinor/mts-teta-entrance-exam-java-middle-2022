package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractServerTest {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractServerTest.class);
  private static Server server;

  @BeforeAll
  static void beforeAll() throws Exception {
    server = new Server();
    server.start();
  }

  @BeforeEach
  void beforeEach() {
    sendMessage("__DELETE_ALL");
  }

  @AfterAll
  static void afterAll() throws Exception {
    server.stop();
  }

  protected String sendMessage(String msg) {
    try (Socket clientSocket = new Socket("localhost", 9090)) {
      LOG.info("Message sent: " + msg);
      new PrintWriter(clientSocket.getOutputStream(), true)
          .println(msg);
      String response =
          new BufferedReader(
              new InputStreamReader(clientSocket.getInputStream())
          ).readLine();
      LOG.info("Response received: " + response);
      return response;
    } catch (IOException e) {
      LOG.error("Error during sending message", e);
      throw new RuntimeException(e);
    }
  }
}
