package com.example.demo;

import java.io.IOException;

public class DemoApplication {

  public static void main(String[] args) throws IOException {
    try {
      var server = new Server();
      server.start();
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
    }
  }
}
