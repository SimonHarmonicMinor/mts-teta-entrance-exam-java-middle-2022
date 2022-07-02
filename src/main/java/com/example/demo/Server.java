package com.example.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

import com.example.demo.model.CommandResponse;
import com.example.demo.service.CommandProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

  private static final Logger LOG = LoggerFactory.getLogger(Server.class);

  private ServerSocket serverSocket;

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
            String request = serverReader.readLine();
            LOG.debug("Request captured: " + request);
            CommandProcessor commandProcessor =  CommandProcessor.getInstance().init(request);
            String response = commandProcessor.getResponse();
            // В реализации по умолчанию в ответе пишется та же строка, которая пришла в запросе
            serverWriter.write(response);
            LOG.debug("Response to the request: " + request);
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