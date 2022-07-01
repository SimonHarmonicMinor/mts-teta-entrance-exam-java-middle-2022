package com.example.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DemoApplication {

  public static void main(String[] args) {
    System.out.println("Hello, world");
    Server myserver = new Server();
    try { myserver.start();
    }
    catch (IOException e) {
    	e.printStackTrace();
    }
    
  }

}
