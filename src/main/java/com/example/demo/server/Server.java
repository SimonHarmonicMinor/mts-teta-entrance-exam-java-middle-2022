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
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

  private static final Logger LOG = LoggerFactory.getLogger(Server.class);

  private final Controller controller;

  private ServerSocket serverSocket;
  private volatile boolean running = false;

  public Server(Controller controller) {
    this.controller = controller;
  }

  public void start() throws IOException {
    serverSocket = new ServerSocket(9090);
    running = true;
    Thread serverThread = new Thread(() -> {
      while (running) {
        this.waitAndHandle();
      }
    });
    serverThread.start();
    LOG.info("Server listens at {}", serverSocket.getLocalSocketAddress());
  }

  private void waitAndHandle() {
    try {
      Socket connection = serverSocket.accept();
      try (
              BufferedReader serverReader = new BufferedReader(
                      new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
              Writer serverWriter = new BufferedWriter(
                      new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8))
      ) {
        String request = serverReader.readLine();
        LOG.debug("Request captured: {}", request);
        final String response = controller.processRequest(request);
        serverWriter.write(response);
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