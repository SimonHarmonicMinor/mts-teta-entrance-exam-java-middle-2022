package com.example.demo;


public class DemoApplication {

  public static void main(String[] args) throws Exception {
    System.out.println("Hello, world");
    Server server = new Server();
    try {
      server.start();
    } catch (Exception e) {
      server.stop();
    }
  }
}
