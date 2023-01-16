package com.example.demo;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DemoApplication extends Server {
  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      String str = reader.readLine();
      if (str.equalsIgnoreCase("exit")) break;
      System.out.println(CommandExecutor.Execute(str));
    }
  }

}
