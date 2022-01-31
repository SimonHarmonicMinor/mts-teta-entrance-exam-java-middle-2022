package com.example.demo.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

  private static final Logger LOG = LoggerFactory.getLogger(Server.class);

  private ServerSocket serverSocket;
  private volatile boolean running = false;

  public void start() throws IOException {
    serverSocket = new ServerSocket(9090);
    running = true;
    Thread serverThread = new Thread(() -> {
      while (running) {
        this.waitAndHandle();
      }
    });
    serverThread.setDaemon(true);
    serverThread.start();
  }

  private void waitAndHandle() {
    try {
      Socket connection = serverSocket.accept();
      try (
              BufferedReader serverReader = new BufferedReader(
                      new InputStreamReader(connection.getInputStream()));
              Writer serverWriter = new BufferedWriter(
                      new OutputStreamWriter(connection.getOutputStream()))
      ) {
        String line = serverReader.readLine();
        LOG.debug("Request captured: {}", line);
        // В реализации по умолчанию в ответе пишется та же строка, которая пришла в запросе
        serverWriter.write(line);
        serverWriter.flush();
      }
    } catch (SocketException e) {
      if (!serverSocket.isClosed()) { // else not an error
        LOG.error("Error during request proceeding", e);
      }
    } catch (Exception e) {
      LOG.error("Error during request proceeding", e);
    }
  }

  public void stop() throws IOException {
    running = false;
    serverSocket.close();
  }
}