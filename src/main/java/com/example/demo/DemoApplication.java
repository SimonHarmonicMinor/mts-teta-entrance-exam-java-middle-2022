package com.example.demo;


import java.io.*;
import java.net.Socket;

public class DemoApplication {

  public static void main(String[] args) throws Exception {

    Server server = new Server();
    server.start();

//    Socket clientSocket = new Socket("localhost", 9090);
//    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//    out.println("testMessage");
//    System.out.println(in.readLine());
    Client client = new Client();
    client.start();
  }

}
