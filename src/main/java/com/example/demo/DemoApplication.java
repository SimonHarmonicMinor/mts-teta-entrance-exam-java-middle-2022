package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DemoApplication {

  public static void main(String[] args) throws Exception {
    Server srvr = new Server();

    System.out.println(srvr.Test("Vasya CREATE_TASK 1stTask"));
    System.out.println(srvr.Test("Vasya CREATE_TASK 2ndTask"));
    System.out.println(srvr.Test("Dima LIST_TASK Vasya"));
  }

}
