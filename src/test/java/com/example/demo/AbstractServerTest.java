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

  private static Socket clientSocket;
  private static PrintWriter out;
  private static BufferedReader in;
  private static Server server;

  @BeforeAll
  static void beforeAll() throws Exception {
    server = new Server();
    server.start();
  }

  @BeforeEach
  void beforeEach() throws Exception {
  }

  @AfterEach
  void afterEach() throws Exception {
  }

  @AfterAll
  static void afterAll() throws Exception {
    server.stop();
  }

  /**
   * Пришлось переделать тест, т.к. сервер по одной команде отрабатывает только
   * будем тогда открывать новый сокет на каждый вызов
   */
  protected String sendMessage(String msg) {
    try {
	    clientSocket = new Socket("localhost", 9090);
	    out = new PrintWriter(clientSocket.getOutputStream(), true);
	    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    out.println(msg);
	    return in.readLine();
    } catch (IOException e) {
    	throw new RuntimeException(e);
    } finally {
    	try {
    		in.close();
    		out.close();
    		clientSocket.close();
    	} catch (IOException e) {
    		; // pass
    	}
    }
  }
}
