package com.example.demo;

import com.example.demo.lexer.SwpLexer;
import com.example.demo.lexer.TokenizeException;
import com.example.demo.model.Result;
import com.example.demo.model.SwpCommand;
import com.example.demo.parser.SwpParseException;
import com.example.demo.parser.SwpParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractServerTest {

  private static Socket clientSocket;
  private static PrintWriter out;
  private static BufferedReader in;
  private static Server server;
  protected static SwpContext context;

//    @BeforeAll
//    static void beforeAll() throws Exception {
//
//    }

  @BeforeEach
  void beforeEach() throws Exception {
    context = new SwpContext();
    server = new Server(context);
    server.start();
//        clientSocket = new Socket("localhost", 9090);
//        out = new PrintWriter(clientSocket.getOutputStream(), true);
//        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  }

  @AfterEach
  void afterEach() throws Exception {
    in.close();
    out.close();
    clientSocket.close();
    server.stop();
  }

//    @AfterAll
//    static void afterAll() throws Exception {
//    }

  protected String sendMessage(String msg) throws IOException {
    clientSocket = new Socket("localhost", 9090);
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    out.println(msg);
    try {
      return in.readLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
