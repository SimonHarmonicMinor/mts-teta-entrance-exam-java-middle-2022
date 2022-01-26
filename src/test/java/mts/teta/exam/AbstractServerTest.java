package mts.teta.exam;

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
  static void beforeAll() {
  }

  @BeforeEach
  void beforeEach() throws Exception {
    server = new Server();
    server.start();

    clientSocket = new Socket("localhost", 9090);
    out = new PrintWriter(clientSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  }

  @AfterEach
  void afterEach() throws Exception {
    in.close();
    out.close();
    clientSocket.close();
    server.stop();
  }

  @AfterAll
  static void afterAll() {
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
