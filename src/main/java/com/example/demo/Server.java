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

public class Server {

  private static final Logger LOG = LoggerFactory.getLogger(Server.class);

  private ServerSocket serverSocket;
  public static Task_list t;

  public void start() throws IOException {
    serverSocket = new ServerSocket(9090);
    System.out.println("Сервер запущен");
    t = new Task_list();
    Thread serverThread = new Thread(() -> {
      while (true) {
        try {
          Socket connection = serverSocket.accept();
          System.out.println("Читаем");
          try (
                  BufferedReader serverReader = new BufferedReader(
                          new InputStreamReader(connection.getInputStream()));
                  Writer serverWriter = new BufferedWriter(
                          new OutputStreamWriter(connection.getOutputStream()));
          ) {
            String line = serverReader.readLine();
            System.out.println(line);
            LOG.debug("Request captured: " + line);
            Result res = Command.proc_command(line);
            serverWriter.write("RESULT" + ' ' + res.toStr() );
            serverWriter.flush();
          }
        } catch (IOException e) {
          LOG.error("Error during request proceeding", e);
          System.out.println("Исключение");
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