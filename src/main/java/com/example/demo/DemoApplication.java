package com.example.demo;


import com.example.demo.service.RequestHandler;
import com.example.demo.service.impl.TaskServiceImpl;

import java.io.IOException;

public class DemoApplication {

  public static void main(String[] args) throws IOException {
    final RequestHandler requestHandler = new RequestHandler(new TaskServiceImpl());
    Server server = new Server(requestHandler);
    server.start();
  }

}
