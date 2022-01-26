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

public class AbstractServerTest {
  private static final String SERVER_ADDRESS = "localhost";
  private static final int SERVER_PORT = 9090;

  private static Socket clientSocket;
  private static PrintWriter out;
  private static BufferedReader in;
  private static Server server;

  @BeforeAll
  static void beforeAll() throws Exception {
    server = new Server();
    server.start(SERVER_PORT);
  }

  @BeforeEach
  void beforeEach() throws Exception {
    clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  }

  @AfterEach
  void afterEach() throws Exception {
    in.close();
    out.close();
    clientSocket.close();
  }

  @AfterAll
  static void afterAll() throws Exception {
    server.stop();
  }

  protected String sendMessage(String msg) {
    out.println(msg);
    try {
      return in.readLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
