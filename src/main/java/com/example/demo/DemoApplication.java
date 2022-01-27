package com.example.demo;

import java.io.IOException;

public class DemoApplication {
  private final static int PORT = 9090;

  public static void main(String[] args) throws IOException {
    try {
      var server = new Server();
      server.start(PORT);
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
    }
  }
}
