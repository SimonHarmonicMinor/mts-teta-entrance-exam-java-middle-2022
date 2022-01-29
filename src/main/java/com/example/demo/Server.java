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

  public void start() throws IOException {
    serverSocket = new ServerSocket(9090);
    Thread serverThread = new Thread(() -> {
      TaskAction task = new TaskAction();
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
            String[] lineComand = line.split(" ");
            if(lineComand.length==3){
              switch (lineComand[1]){
                case "CREATE_TASK":
                  line = task.createTask(lineComand[0],lineComand[3]);
                  break;
                case "DELETE_TASK":
                  line = task.deletedTask(lineComand[0],lineComand[3]);
                  break;
                case "CLOSE_TASK":
                  line = task.closedTask(lineComand[0],lineComand[3]);
                  break;
                case "REOPEN_TASK":
                  line = task.reOpenTask(lineComand[0],lineComand[3]);
                  break;
                case "LIST_TASK":
                  line = "TASKS " + task.listTaskUser(lineComand[3]);
                  break;
                  default:
                    line = "ERROR Invalid command";
                    break;
              }
            }else{
              line = "WRONG_FORMAT";
            }
            LOG.debug("Request captured: " + line);
            // В реализации по умолчанию в ответе пишется та же строка, которая пришла в запросе
            serverWriter.write(line);
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