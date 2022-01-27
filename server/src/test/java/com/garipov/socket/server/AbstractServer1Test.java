package com.garipov.socket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class AbstractServer1Test {

  private static Socket clientSocket;
  private static PrintWriter out;
  private static BufferedReader in;
  private static Server1 server1;

  @BeforeAll
  static void beforeAll() throws Exception {
    server1 = new Server1();
    server1.start();
  }

  @BeforeEach
  void beforeEach() throws Exception {
    clientSocket = new Socket("localhost", 9090);
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
    server1.stop();
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
