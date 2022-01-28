package com.example.demo;


import java.io.IOException;

public class DemoApplication {

  public static void main(String[] args) throws IOException {
    System.out.println("Hello, world");
    Server server = new Server();
    server.start();
  }

}
