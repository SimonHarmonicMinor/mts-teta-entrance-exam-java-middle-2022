package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractServerTest {

  private static Socket clientSocket;
  private static PrintWriter out;
  private static BufferedReader in;
  private static Server server;

  private static final Logger logger = LoggerFactory.getLogger(AbstractServerTest.class);

  @BeforeAll
  static void beforeAll() throws Exception {
    server = new Server();
    server.start();
    logger.info("Start server.");
  }

  @BeforeEach
  void beforeEach() throws Exception {
    clientSocket = new Socket("localhost", 9090);
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    logger.info("Client is created.");
  }

  @AfterEach
  void afterEach() throws Exception {
    in.close();
    out.close();
    clientSocket.close();
    logger.info("Close all.");
  }

  @AfterAll
  static void afterAll() throws Exception {
    server.stop();
    logger.info("Server is stopped.");
  }

  protected String sendMessage(String msg) {
    out.println(msg);
    logger.info("Client send = "+ msg);
    try {
      return in.readLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
