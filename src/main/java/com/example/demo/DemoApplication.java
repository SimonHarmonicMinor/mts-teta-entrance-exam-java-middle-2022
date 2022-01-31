package com.example.demo;


import com.example.demo.server.Controller;
import com.example.demo.parser.RequestParser;
import com.example.demo.server.Server;
import com.example.demo.task.TaskRepository;
import com.example.demo.task.TaskService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DemoApplication {
  private static final Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

  public static void main(String[] args) {
    final var server = new Server(initController(new TaskRepository()));
    try {
      server.start();
    } catch (IOException e) {
      LOG.error("Error starting server: ", e);
    }
  }

  // package-private for tests only
  static Controller initController(TaskRepository repo) {
    final var parser = new RequestParser();
    final var taskService = new TaskService(repo);
    return new Controller(parser, taskService);
  }
}
