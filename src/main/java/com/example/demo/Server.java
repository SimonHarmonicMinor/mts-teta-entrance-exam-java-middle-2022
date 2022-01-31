package com.example.demo;

import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TaskRepositoryImpl;
import com.example.demo.service.command.CommandService;
import com.example.demo.service.command.CommandServiceImpl;
import com.example.demo.service.mapper.CommandMapper;
import com.example.demo.service.mapper.CommandMapperImpl;
import com.example.demo.service.task.TaskService;
import com.example.demo.service.task.TaskServiceImpl;
import com.example.demo.service.validation.ValidationService;
import com.example.demo.service.validation.ValidationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private CommandMapper commandMapper;
    private CommandService commandService;
    private ServerSocket serverSocket;
    private TaskRepository taskRepository;
    private TaskService taskService;
    private ValidationService validationService;

    {
      validationService = new ValidationServiceImpl();
      commandMapper = new CommandMapperImpl();
      taskRepository = new TaskRepositoryImpl();
      taskService = new TaskServiceImpl(taskRepository);
      commandService = new CommandServiceImpl(commandMapper, taskService, validationService);
    }

  public void start() throws IOException {
    serverSocket = new ServerSocket(9090);
    Thread serverThread = new Thread(() -> {
      while (true) {
        try {
          Socket connection = serverSocket.accept();
          try (
              BufferedReader serverReader = new BufferedReader(
                  new InputStreamReader(connection.getInputStream()));
              Writer serverWriter = new BufferedWriter(
                  new OutputStreamWriter(connection.getOutputStream()));
          ) {
            String command = serverReader.readLine();
            LOG.debug("Request captured: " + command);
            String response = commandService.execute(command);
            // В реализации по умолчанию в ответе пишется та же строка, которая пришла в запросе
            serverWriter.write(response);
            serverWriter.flush();
          }
        } catch (Exception e) {
          LOG.error("Error during request proceeding", e);
          break;
        }
      }
    });
    serverThread.setDaemon(true);
    serverThread.start();
  }

  public void stop() throws Exception {
    serverSocket.close();
  }

  public TaskRepository getTaskRepository() {
    return taskRepository;
  }
}