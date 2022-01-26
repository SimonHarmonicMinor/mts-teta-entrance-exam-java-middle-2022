package com.example.demo;

public class DemoApplication {
  private static final String SERVER_ADDRESS = "localhost";
  private static final int SERVER_PORT = 9090;

  public static void main(String[] args) throws Exception {
    Server server = new Server();
    Client client = new Client();

    server.start(SERVER_PORT);
    client.start(SERVER_ADDRESS, SERVER_PORT);
  }

}
