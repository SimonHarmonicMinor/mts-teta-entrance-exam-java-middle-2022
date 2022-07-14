package com.example.demo;

import com.example.models.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  private static final Logger LOG = LoggerFactory.getLogger(Server.class);

  private ServerSocket serverSocket;

  private TaskManagerImpl taskManager;

  public void start() throws IOException {
    taskManager = new TaskManagerImpl();
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
            String line = serverReader.readLine();
            LOG.debug("Request captured: " + line);

            line = taskManager.validateRequest(line);
            Response response = taskManager.call(line);

            serverWriter.write(response.getAnswer());
            serverWriter.flush();
          }
        } catch (Exception e) {
          LOG.error("Error during request proceeding", e);
          break;
        }
      }
    });
    serverThread.start();
  }

  public void stop() throws Exception {
    serverSocket.close();
  }
}