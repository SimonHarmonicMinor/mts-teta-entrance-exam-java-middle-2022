package com.example.demo;


import java.io.IOException;

public class DemoApplication {

  public static void main(String[] args) throws Exception {
    System.out.println("Hello, world");

    Server server = new Server();

    try {
      server.start();
    } catch (IOException e) {
      server.stop();
    }
  }

}
