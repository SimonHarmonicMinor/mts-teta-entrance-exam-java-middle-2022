package com.example.demo;

import java.io.IOException;
import java.util.Scanner;

public class DemoApplication {

  public static void main(String[] args) {
    //System.out.println("Hello, world");
    Scanner sc = new Scanner(System.in);
    Server s = new Server();
    try {
      s.start();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
