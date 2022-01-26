package com.example.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.example.command.CommandProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

  private static final Logger LOG = LoggerFactory.getLogger(Server.class);
  private CommandProcessor commandProcessor;
  private ServerSocket serverSocket;
  public Server(){
    this.commandProcessor = new CommandProcessor();
  }

  public void start() throws IOException {
    serverSocket = new ServerSocket(9090,0, InetAddress.getLoopbackAddress());
    Thread serverThread = new Thread(() -> {
      while (true) {
        try {
          Socket connection = serverSocket.accept();
          try (
              BufferedReader serverReader = new BufferedReader(
                  new InputStreamReader(connection.getInputStream()));
              Writer serverWriter = new BufferedWriter(
                  new OutputStreamWriter(connection.getOutputStream()))
          ) {
            String line = serverReader.readLine();
            LOG.debug("Request captured: " + line);

            // В реализации по умолчанию в ответе пишется та же строка, которая пришла в запросе
            serverWriter.write(this.commandProcessor.ProcessCommand(line));
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
}