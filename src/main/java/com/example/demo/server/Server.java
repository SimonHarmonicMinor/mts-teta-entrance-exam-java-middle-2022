package com.example.demo.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

import com.example.demo.core.processors.RequestProcessor;
import com.example.demo.core.data.Response;
import com.example.demo.core.enums.Result;
import com.example.demo.core.exceptions.AccessDeniedException;
import com.example.demo.core.exceptions.ErrorException;
import com.example.demo.core.exceptions.WrongFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

  private static final Logger LOG = LoggerFactory.getLogger(Server.class);
  private final int DEFAULT_PORT = 9090;
  private ServerSocket serverSocket;
  private RequestProcessor requestProcessor;

  public Server() {
    this.requestProcessor = new RequestProcessor();
  }

  public static void main(String[] args) throws IOException {
    Server server = new Server();
    server.start();
  }

  public void start() throws IOException {
    LOG.info("Сервер запущен на порту " + DEFAULT_PORT);
    serverSocket = new ServerSocket(DEFAULT_PORT);
    Thread serverThread =
        new Thread(
            () -> {
              while (true) {
                try {
                  LOG.info("Сервер ожидает подключения");
                  Socket connection = serverSocket.accept();

                  LOG.info("Клиент подключён");
                  try (BufferedReader serverReader =
                          new BufferedReader(new InputStreamReader(connection.getInputStream()));
                      Writer serverWriter =
                          new BufferedWriter(
                              new OutputStreamWriter(connection.getOutputStream())); ) {
                    String request = "";
                    while (true) {
                      try {
                        request = serverReader.readLine();
                        LOG.info("Принят запрос от клиента: " + request);
                        if (request.equals("EXIT")) {
                          LOG.info("Выполнена команда 'EXIT'. Клиент отключён");
                          break;
                        }
                        Response response = this.processRequest(request);
                        send(serverWriter, response.toString());
                      } catch (WrongFormatException wrongFormatException) {
                        LOG.error(request + "\n" + wrongFormatException.getMessage());
                        send(serverWriter, Result.WRONG_FORMAT.toString());
                      } catch (AccessDeniedException accessDeniedException) {
                        LOG.error(request + "\n" + accessDeniedException.getMessage());
                        send(serverWriter, Result.ACCESS_DENIED.toString());
                      } catch (ErrorException errorException) {
                        LOG.error(request + "\n" + errorException.getMessage());
                        send(serverWriter, Result.ERROR.toString());
                      }
                    }
                  }
                } catch (Exception exception) {
                  LOG.error("Error during request proceeding", exception);
                  // break;
                }
              }
            });
    //    serverThread.setDaemon(true);
    serverThread.start();
  }

  public void stop() throws Exception {
    serverSocket.close();
  }

  private void send(Writer serverWriter, String message) throws IOException {
    serverWriter.write(message + "\n");
    serverWriter.flush();
  }

  private Response processRequest(String request) {
    return this.requestProcessor.processRequest(request);
  }
}
